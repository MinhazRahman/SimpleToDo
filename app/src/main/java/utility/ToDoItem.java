package utility;

import java.io.Serializable;
import java.util.Objects;

public class ToDoItem implements Serializable {

    private String itemDescription;
    private boolean hasReminder;
    private String dateOfCreation;
    private String reminderDate;
    private String reminderTime;
    private String priorityStatus;

    public ToDoItem(String itemDescription, String dateOfCreation){
        this.itemDescription = itemDescription;
        this.dateOfCreation = dateOfCreation;
    }

    public ToDoItem(String itemDescription, boolean hasReminder, String dateOfCreation, String reminderDate, String reminderTime, String priorityStatus) {
        this.itemDescription = itemDescription;
        this.hasReminder = hasReminder;
        this.dateOfCreation = dateOfCreation;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
        this.priorityStatus = priorityStatus;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public boolean isHasReminder() {
        return hasReminder;
    }

    public void setHasReminder(boolean hasReminder) {
        this.hasReminder = hasReminder;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getPriorityStatus() {
        return priorityStatus;
    }

    public void setPriorityStatus(String priorityStatus) {
        this.priorityStatus = priorityStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoItem toDoItem = (ToDoItem) o;
        return hasReminder == toDoItem.hasReminder &&
                Objects.equals(itemDescription, toDoItem.itemDescription) &&
                Objects.equals(dateOfCreation, toDoItem.dateOfCreation) &&
                Objects.equals(reminderDate, toDoItem.reminderDate) &&
                Objects.equals(reminderTime, toDoItem.reminderTime) &&
                Objects.equals(priorityStatus, toDoItem.priorityStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemDescription, hasReminder, dateOfCreation, reminderDate, reminderTime, priorityStatus);
    }

    @Override
    public String toString() {
        return "ToDoItem{" +
                "itemDescription='" + itemDescription + '\'' +
                ", hasReminder=" + hasReminder +
                ", dateOfCreation='" + dateOfCreation + '\'' +
                ", reminderDate='" + reminderDate + '\'' +
                ", reminderTime='" + reminderTime + '\'' +
                ", priorityStatus='" + priorityStatus + '\'' +
                '}';
    }
}
