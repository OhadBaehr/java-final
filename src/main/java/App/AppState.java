package App;

import UI.Utils;
import UI.ViewManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppState {
    public static Map<Integer, List<String[]>> expenses=new HashMap<Integer,List<String[]>>();

    public static List<String> categories;
    public static int activeCategory;
    static public String earliestExpenseDate()  {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date timestamp=Calendar.getInstance().getTime();
        List <String []> expenses=AppState.expenses.get(AppState.activeCategory);
        for(int i=0;i<expenses.size();i++){
            try{
                Date time=format.parse(expenses.get(i)[0]);
                if(timestamp.after(time)) timestamp=time;
            }catch (ParseException e){

            }
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(timestamp);
    }

    static public String latestExpenseDate()  {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try{
            Date timestamp= Utils.earliestDate(format.parse(earliestExpenseDate()), format.parse(ViewManager.dateFilterTextFieldB.getText()));
            List <String []> expenses=AppState.expenses.get(AppState.activeCategory);
            for(int i=0;i<expenses.size();i++){
                    Date time=format.parse(expenses.get(i)[0]);
                    if(timestamp.before(time)) timestamp=time;

            }
            return new SimpleDateFormat("dd/MM/yyyy").format(timestamp);
        }catch (ParseException e){

        }
        return Utils.now();
    }
    public static String[][] getExpenses(){
        Object[] expenses=AppState.expenses.get(AppState.activeCategory).toArray();
        return Arrays.copyOf(expenses,expenses.length,String[][].class);
    }
    public static void initDefaultState(){
        categories= new ArrayList<>();
        categories.add("Household");
        categories.add("Work");
        categories.add("Groceries");
        activeCategory=0;
        expenses.put(0,new ArrayList<String[]>());
        expenses.put(1,new ArrayList<String[]>());
        expenses.put(2,new ArrayList<String[]>());
    }
}
