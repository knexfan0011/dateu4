package com.tutego.dateu4;

import org.springframework.web.bind.annotation.*;

import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class StatisticRestController {

    @RequestMapping( "/api/stat/total" )
    public int totalNumberOfRegisteredUnicorns() {
        return 4;
    }

    @RequestMapping(value="/api/stat/chart")
    ChartData chart(){
        ChartData cd = new ChartData();
        int[] a0 = new int[12];
        int[] a1 = new int[12];
        for (int i = 0; i < 12; i++){
            a0[i] = (int)(Math.random()*41-10);
            a1[i] = (int)(Math.random()*41-10);
        }
        cd.data = Arrays.asList(a0, a1);
        return cd;
    }


}
class ChartData {
    public List<int[]> data;
}