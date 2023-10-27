package com.yusufmirza.etubilgisayartopluluk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ArrayListSorter<E extends Helper>  {



    public ArrayListSorter() {



    }

    public ArrayList<E> sortedArray(ArrayList<E> myArray) {

        Collections.sort(myArray, new Comparator<E>() {
            @Override
            public int compare(E obj1, E obj2) {
                // İki MyObject nesnesini "name" alanına göre karşılaştırın
                return obj1.getName().compareTo(obj2.getName());
            }
        });

        return myArray;


    }
}







