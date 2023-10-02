package com.ivy.piechart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.ivy.base.legacy.Transaction
import com.ivy.base.model.TransactionType
import com.ivy.data.db.dao.read.SettingsDao
import com.ivy.domain.ComposeViewModel
import com.ivy.legacy.IvyWalletCtx
import com.ivy.legacy.data.SharedPrefs
import com.ivy.legacy.data.model.TimePeriod
import com.ivy.legacy.datamodel.Category
import com.ivy.legacy.utils.ioThread
import com.ivy.navigation.PieChartStatisticScreen
import com.ivy.piechart.action.PieChartAct
import com.ivy.wallet.ui.theme.modal.ChoosePeriodModalData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PieChartStatisticViewModel @Inject constructor(
    private val settingsDao: SettingsDao,
    private val ivyContext: IvyWalletCtx,
    private val pieChartAct: PieChartAct,
    private val sharedPrefs: SharedPrefs
) : ComposeViewModel<PieChartStatisticState, PieChartStatisticEvent>() {

    private val treatTransfersAsIncomeExpense = mutableStateOf(false)
    private val transactionType = mutableStateOf(TransactionType.INCOME)
    private val period = mutableStateOf(TimePeriod())
    private val baseCurrency = mutableStateOf("")
    private val totalAmount = mutableDoubleStateOf(0.0)
    private val categoryAmounts = mutableStateOf(persistentListOf<CategoryAmount>())
    private val selectedCategory = mutableStateOf<SelectedCategory?>(null)
    private val accountIdFilterList = mutableStateOf<ImmutableList<UUID>>(persistentListOf())
    private val showCloseButtonOnly = mutableStateOf(false)
    private val filterExcluded = mutableStateOf(false)
    private val transactions = mutableStateOf<ImmutableList<Transaction>>(persistentListOf())
    private val choosePeriodModal = mutableStateOf<ChoosePeriodModalData?>(null)

    fun start(
        screen: PieChartStatisticScreen
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            startInternally(
                period = ivyContext.selectedPeriod,
                type = screen.type,
                accountIdFilterList = screen.accountList,
                filterExclude = screen.filterExcluded,
                transactions = screen.transactions,
                transfersAsIncomeExpenseValue = screen.treatTransfersAsIncomeExpense
            )
        }
    }

    @Composable
    override fun uiState(): PieChartStatisticState {
        return PieChartStatisticState(
            transactionType =,
            period =,
            baseCurrency =,
            totalAmount =,
            categoryAmounts =,
            selectedCategory =,
            accountIdFilterList =,
            showCloseButtonOnly =,
            filterExcluded =,
            transactions =,
            choosePeriodModal =
        )
    }

    private suspend fun startInternally(
        period: TimePeriod,
        type: TransactionType,
        accountIdFilterList: ImmutableList<UUID>,
        filterExclude: Boolean,
        transactions: ImmutableList<Transaction>,
        transfersAsIncomeExpenseValue: Boolean
    ) {
        initialise(period, type, accountIdFilterList, filterExclude, transactions)
        treatTransfersAsIncomeExpense.value = transfersAsIncomeExpenseValue
        load(period = period)
    }

    private suspend fun initialise(
        period: TimePeriod,
        type: TransactionType,
        accountIdFilterList: ImmutableList<UUID>,
        filterExclude: Boolean,
        transactions: ImmutableList<Transaction>
    ) {
        val settings = ioThread { settingsDao.findFirst() }
        val baseCurrency = settings.currency

        updateState {
            it.copy(
                period = period,
                transactionType = type,
                accountIdFilterList = accountIdFilterList,
                filterExcluded = filterExclude,
                transactions = transactions,
                showCloseButtonOnly = transactions.isNotEmpty(),
                baseCurrency = baseCurrency
            )
        }
    }

    private suspend fun load(
        period: TimePeriod
    ) {
        val type = stateVal().transactionType
        val accountIdFilterList = stateVal().accountIdFilterList
        val transactions = stateVal().transactions
        val baseCurrency = stateVal().baseCurrency
        val range = period.toRange(ivyContext.startDayOfMonth)

        val treatTransferAsIncExp =
            sharedPrefs.getBoolean(
                SharedPrefs.TRANSFERS_AS_INCOME_EXPENSE,
                false
            ) && accountIdFilterList.isNotEmpty() && treatTransfersAsIncomeExpense.value

        val pieChartActOutput = ioThread {
            pieChartAct(
                PieChartAct.Input(
                    baseCurrency = baseCurrency,
                    range = range,
                    type = type,
                    accountIdFilterList = accountIdFilterList,
                    treatTransferAsIncExp = treatTransferAsIncExp,
                    existingTransactions = transactions,
                    showAccountTransfersCategory = accountIdFilterList.isNotEmpty()
                )
            )
        }

        val totalAmount = pieChartActOutput.totalAmount
        val categoryAmounts = pieChartActOutput.categoryAmounts

        updateState {
            it.copy(
                period = period,
                totalAmount = totalAmount,
                categoryAmounts = categoryAmounts,
                selectedCategory = null
            )
        }
    }

    private suspend fun onSetPeriod(period: TimePeriod) {
        ivyContext.updateSelectedPeriodInMemory(period)
        load(
            period = period
        )
    }

    private suspend fun nextMonth() {
        val month = stateVal().period.month
        val year = stateVal().period.year ?: com.ivy.legacy.utils.dateNowUTC().year
        if (month != null) {
            load(
                period = month.incrementMonthPeriod(ivyContext, 1L, year)
            )
        }
    }

    private suspend fun previousMonth() {
        val month = stateVal().period.month
        val year = stateVal().period.year ?: com.ivy.legacy.utils.dateNowUTC().year
        if (month != null) {
            load(
                period = month.incrementMonthPeriod(ivyContext, -1L, year)
            )
        }
    }

    private suspend fun configureMonthModal(timePeriod: TimePeriod?) {
        val choosePeriodModalData = if (timePeriod != null) {
            ChoosePeriodModalData(period = timePeriod)
        } else {
            null
        }

        updateState {
            it.copy(choosePeriodModal = choosePeriodModalData)
        }
    }

    private suspend fun onCategoryClicked(clickedCategory: Category?) {
        val selectedCategory = if (clickedCategory == stateVal().selectedCategory?.category) {
            null
        } else {
            SelectedCategory(category = clickedCategory)
        }

        val existingCategoryAmounts = stateVal().categoryAmounts
        val newCategoryAmounts = if (selectedCategory != null) {
            existingCategoryAmounts
                .sortedByDescending { it.amount }
                .sortedByDescending {
                    selectedCategory.category == it.category
                }
        } else {
            existingCategoryAmounts.sortedByDescending {
                it.amount
            }
        }.toImmutableList()

        updateState {
            it.copy(
                selectedCategory = selectedCategory,
                categoryAmounts = newCategoryAmounts
            )
        }
    }

    override fun onEvent(event: PieChartStatisticEvent) {
        viewModelScope.launch(Dispatchers.Default) {
            when (event) {
                is PieChartStatisticEvent.OnSelectNextMonth -> nextMonth()
                is PieChartStatisticEvent.OnSelectPreviousMonth -> previousMonth()
                is PieChartStatisticEvent.OnSetPeriod -> onSetPeriod(event.timePeriod)
                is PieChartStatisticEvent.OnShowMonthModal -> configureMonthModal(event.timePeriod)
                is PieChartStatisticEvent.OnCategoryClicked -> onCategoryClicked(event.category)
            }
        }
    }
}