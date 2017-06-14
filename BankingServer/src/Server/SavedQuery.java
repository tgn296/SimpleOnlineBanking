/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

import java.util.ArrayList;

/**
 *
 * @author Mr-Tuy
 */
public class SavedQuery {
    private ArrayList<String> updateQuery;
    public SavedQuery(){
        this.updateQuery = new ArrayList<String>();
    }
    public void addQuery(String query){
        this.updateQuery.add(query);
    }
    public void clearQuery(){
        this.updateQuery.clear();
    }
    public ArrayList<String> getSavedQuery(){
        return this.updateQuery;
    }
    
}
