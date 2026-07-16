package com.hooni.model;


public class ContactBean
{
	    public String getEmail() { return _email; }
	    void setEmail(String email) { _email = email; }
	    
	    public String getCemail() { return _cemail; }
	    void setCemail(String email) { _cemail = email; }
	    
	    public String getTopic() { return _topic; }
	    void setTopic(String topic) {_topic = topic; }
	    
	    public String getOrderNum() { return _orderNum; }
	    void setOrderNum(String orderNum) {_orderNum = orderNum; }
	    
	    public String getVsc() { return _vsc; }
	    void setVsc(String vsc) {_vsc = vsc; }
	    
	    public String getQuestion() { return _question; }
	    public void setQuestion(String question) {_question = question; }
	    
	    private String _email;
	    private String _cemail;
	    private String _topic;
	    private String _orderNum;
	    private String _question;
	    private String _vsc;
}
