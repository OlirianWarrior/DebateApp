package com.gmail.donncha.assignment;

/**
 * Created by donncha on 11/23/2015.
 */
public class DebateInfo {

    String question, topic, username;
    int yesVote, noVote;

    public void setQuestion(String question)
    {
        this.question = question;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getTopic()
    {
        return this.topic;
    }

    public void setYesVote(int yesVote)
    {
        this.yesVote = yesVote;
    }

    public int getYesVote()
    {
        return this.yesVote;
    }

    public void setNoVote(int noVote)
    {
        this.noVote = noVote;
    }

    public int getNoVote()
    {
        return this.noVote;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return this.username;
    }

}
