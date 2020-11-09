package edu.mum.common;

public class StatusCode {
    public static final int OK=20000;//suc

    public static final int ERROR=20001;//fail

    public static final int LOGINERROR=20002;//username or passwd fail

    public static final int ACCESSERROR=20003;// no right

    public static final int REMOTEERROR=20004;//call fail

    public static final int REPERROR=20005;//repetitive operation
}
