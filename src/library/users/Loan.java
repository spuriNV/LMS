package library.users;

import Item.item;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.Objects;

public class Loan {

    private Date issue_date;
    private Date return_date;

    private item borrowed_item;
    private boolean isLate;

    public Loan(Date issue_date, item borrowed_item, boolean isLate) {
        this.issue_date = issue_date;
        this.borrowed_item = borrowed_item;
        this.isLate = isLate;
        setReturn_date(issue_date);
    }

    public Date getIssue_date() { return issue_date;}

    public void setIssue_date(Date issue_date) {
        this.issue_date = issue_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date issue_date) {
        Calendar c = Calendar.getInstance();
        c.setTime(issue_date);
        c.add(Calendar.DATE, 14);
        this.return_date = c.getTime();
    }

    public item getBorrowed_item() {
        return borrowed_item;
    }

    public void setBorrowed_item(item borrowed_item) {
        this.borrowed_item = borrowed_item;
    }

    public boolean getLate() {
        Date thisDate = new Date();
        if(thisDate.after(this.return_date) ) {
            return true;
        }
        return false;
    }

    public void setLate(boolean late) {
        isLate = late;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Loan)) {
            return false;
        }
        Loan loan = (Loan) o;
        return isLate == loan.isLate &&
                Objects.equals(issue_date.getDate(), loan.getIssue_date().getDate()) &&
                Objects.equals(return_date.getDate(), loan.getReturn_date().getDate()) &&
                Objects.equals(borrowed_item, loan.getBorrowed_item());
    }

    @Override
    public int hashCode() {
        return Objects.hash(issue_date.getDate(), isLate, return_date.getDate(), borrowed_item);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "issue_date=" + issue_date +
                ", return_date=" + return_date +
                ", borrowed_item=" + borrowed_item +
                ", isLate=" + isLate +
                '}';
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String thisIssueDate = formatter.format(issue_date);
        String thisReturnDate = formatter.format(return_date);


        json.put("issue_date", thisIssueDate);
        json.put("return_date", thisReturnDate);
        json.put("borrowed_item", borrowed_item.toJson());
        json.put("late_status", isLate);
        return json;
    }

}
