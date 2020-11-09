package com.max.payment.VO;

public class NormalVO {
    public static ResultVO<String> normalReturn(String message) {
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData(message);
        return resultVO;
    }

    public static ResultVO<String> failReturn(String message) {
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(1);
        resultVO.setMsg("fail");
        resultVO.setData(message);
        return resultVO;
    }
}
