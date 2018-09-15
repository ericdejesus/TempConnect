package com.example.edejesus1097.miniproj;
import java.util.*;
public class TempTime {
   public List<Integer> Time = new ArrayList();
   public List<Integer> Temp = new ArrayList();

    public TempTime(){}

    public TempTime(Integer inTime[],Integer inTemp[],Integer size) {

        //inserting list into the class time and temperature
        if (size == inTemp.length && size == inTime.length) {
            for(int i =0; i <size ; i++)
            {
                Time.add(inTime[i]);
                Temp.add(inTemp[i]);
            }

        }
    }

}
