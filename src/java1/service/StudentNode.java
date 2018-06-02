package java1.service;
import java.util.ArrayList;
import java.util.List;

import java1.common.ServerResponse;

/**
 * Created by 张柏桦 on 2018/2/28.
 */
public class StudentNode {

    private String name;
    private String ID;
    private String sex;
    private String grade;
    private String major;
    private HobbyList hobbyList;
    private StudentNode next;

    public StudentNode(){
        this.name = this.ID = this.grade = this.major = null;
        this.sex = null;
        this.hobbyList = null;
        this.next = null;
    }

    public StudentNode(String name, String ID, String grade, String major, String sex){
        this.grade = grade; this.major = major; this.sex = sex;
        this.name = name; this.ID = ID; this.next = null;
        this.hobbyList = null;
    }

    public StudentNode(String name, String ID){
        this.name = name; this.ID = ID; this.next = null;
        this.hobbyList = null;
    }

    public StudentNode(StudentNode stu){
        this(stu.name, stu.ID, stu.grade, stu.major, stu.sex);
    }

    public ServerResponse addHobby(HobbyNode hobby){
        if (hobby == null){
            return ServerResponse.createByErrorMsg("hobby is null");
        }
        if (hobbyList == null){
            hobbyList = new HobbyList();
        }
        HobbyNode newHobby = new HobbyNode(hobby.getID(), hobby.getHobbyCategoryID(), hobby.getHobby());

        return hobbyList.addHobby(newHobby);
    }

    public ServerResponse removeHobby(String ID){
        if (ID == null){
            return ServerResponse.createByErrorMsg("ID is null");
        }
        if (hobbyList == null){
            return ServerResponse.createByErrorMsg("hobby list is null");
        }
        return hobbyList.delHobby(ID);
    }

    public boolean isHobbyExist(String hobbyID){
        if (this.hobbyList == null || this.hobbyList.getHead().getNext() == null){
            return false;
        }
        HobbyNode temp = this.hobbyList.getHead();
        while (temp.getNext() != null){
            if (temp.getNext().getID().equals(hobbyID)){
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }

//    从学生兴趣中删除带有相应CategoryID 的兴趣
    public ServerResponse removeHobbyWithHobbyCategoryID(String hobbyCategoryID){

        List<String> IDs = new ArrayList<String>();
        if (hobbyList == null || hobbyList.getHead() == null){
            return ServerResponse.createBySuccess();
        }
        HobbyNode temp = hobbyList.getHead();
        while (temp.getNext() != null){
            if (temp.getNext().getHobbyCategoryID().equals(hobbyCategoryID)){
                IDs.add(temp.getNext().getID());
            }
            temp = temp.getNext();
        }
        for (String ID : IDs){
            removeHobby(ID);
        }

        return ServerResponse.createBySuccess();
    }
    
//    修改学生基本信息
    public ServerResponse modifyBasicInfo(String stuName, String stuSex, String stuGrade, String stuMajor){
        name = stuName; sex = stuSex;
        grade = stuGrade; major = stuMajor;

        return ServerResponse.createBySuccessMsg("修改成功！");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public StudentNode getNext() {
        return next;
    }

    public void setNext(StudentNode next) {
        this.next = next;
    }

    public HobbyList getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(HobbyList hobbyList) {
        this.hobbyList = hobbyList;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
