package com.ssi.fctrading.DataContract;
import java.util.ArrayList;
public class RatelimitResponse extends ArrayList<RatelimitResponse.RatelimitItem>{
    public class RatelimitItem {
        public String endpoint;
        public String period ;
        public long limit;
    }
}

