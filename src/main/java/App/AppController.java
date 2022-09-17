package App;

import UI.ViewManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppController {
    static public void removeExpense(int modelRow){
        AppState.expenses.get(AppState.activeCategory).remove(modelRow);
        ((DefaultTableModel) ViewManager.table.getModel()).removeRow(modelRow);
        ViewManager.refreshDates();
    }


    static public void addExpense(String [] row){
        AppState.expenses.get(AppState.activeCategory).add(row);
        ((DefaultTableModel) ViewManager.table.getModel()).addRow(row);
        ViewManager.refreshDates();
    }
    static public void removeCurrentCategory(){
        AppState.categories.remove(AppState.activeCategory);
        Object [] categories=AppState.categories.toArray();
        String [] items= Arrays.copyOf(categories, categories.length, String[].class);
        if(items.length!=0){
            AppState.activeCategory=0;
            ViewManager.refreshAppView();
        }else{
            addCategory();
        }

    }

    static public List <String []> dateFilteredExpenses(String dateA,String dateB){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        try{
            List <String []> filtered=new ArrayList<String[]>();
            Date aA=format.parse(dateA);
            Date bB=format.parse(dateB);
            Date a= aA.before(bB)?aA:bB;
            Date b= aA.before(bB)?bB:aA;

            for (String[] expense : AppState.expenses.get(AppState.activeCategory) ){
                Date expenseTimestamp = format.parse(expense[0]);
                if((a.before(expenseTimestamp) || a.equals(expenseTimestamp)) && (b.after(expenseTimestamp) || b.equals(expenseTimestamp))) filtered.add(expense);
            }
            return filtered;
        }catch (ParseException e){

        }
        return AppState.expenses.get(AppState.activeCategory);
    }
    static public void addCategory(){
        AppState.categories.add("New Category");
        AppState.activeCategory=AppState.categories.size()-1;
        AppState.expenses.put(AppState.activeCategory, new ArrayList<String[]>());

        ViewManager.refreshAppView();
    }
    static public void updateActiveCategory(String str){

        try{
            AppState.expenses.put(AppState.activeCategory, AppState.expenses.remove(AppState.activeCategory));
        }catch (Exception e){
            System.out.println(e);
        }

        AppState.categories.set(AppState.activeCategory,str);
        ViewManager.refreshAppView();
    }

    static public void setActiveCategory(int index){
        AppState.activeCategory=index;
        ViewManager.refreshAppView();
    }
}
