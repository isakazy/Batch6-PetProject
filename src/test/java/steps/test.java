package steps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class test {
    public static void main(String[] args) {

        List<String> names = new ArrayList<>();

        names.add("Isa");
        names.add("afd");
        names.add("adsfaf");
        names.add("Isa");
        names.add("Iadffdsa");
        names.add("Isa");
        names.add("wfasd");
        names.add("Isaffaa");
        names.add("Nurgazy");
        names.add("af");
        names.add("Isa");


        test test = new test();
        System.out.println(test.isPresent(names));

    }


    public boolean isPresent(List<String> names ){

        int size = names.size();
        for(int i = 0; i < size - 1; i ++ ){

            if(names.get(i).equals( "Nurgazy")){
                return true;
            }
        }
        return  false;
    }
}
