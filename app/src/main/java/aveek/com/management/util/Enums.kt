package aveek.com.management.util

enum class EnumEventState(val type: String) {
    PROCEED("proceed"),
    DISMISS("dismiss"),
    CANCEL("cancel")
}

/** Represents types of views to load inside recycler view
 * @author Aveek
 * @version 1
 * @since Version - 1.0
 */
enum class EnumTransactionType(val type: String) {
    REGULAR("regular"),
    LOADING("loading")
}

/** Represents types of Data to pass when any call takes place
 * @author Aveek
 * @version 1
 * @since Version - 1.0
 */
enum class EnumDataState(val type: String) {
    SUCCESS("success"),
    ERROR("error"),
    FAILED("failed")
}