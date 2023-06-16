package library.users;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoanList {

    private ArrayList<Loan> loans;


    public LoanList(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    public void addToList(Loan loan) {
        loans.add(loan);
    }

    public void removeFromList(Loan loan) {
        loans.remove(loan);
    }

    public double calculateFine() {
        double sum = 0;
        for(Loan loan : loans) {
            if(loan.getLate()) {
                sum += loan.getBorrowed_item().getCost();
            }
        }
        return sum;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("loansList", listToJson());
        return json;
    }

    // EFFECTS: returns tasks in this todolist as a JSON array

    private JSONArray listToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Loan loan : loans) {
            jsonArray.put(loan.toJson());
        }
        return jsonArray;
    }
}
