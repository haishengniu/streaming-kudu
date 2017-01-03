package cn.udrm.bigdata.pr100;

import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by A443FF09 on 2016/12/27.
 */
public class Pscan_type implements Serializable {
    int StartFreq_low;
    int  StopFreq_low;
    int StepFreq;
    int StartFreq_high;
    int StopFreq_high;
    String reserved;
    Timestamp OutputTimestamp;
}
