package java1.service;
import java1.common.ServerResponse;

/**
 * Created by 张柏桦 on 2018/3/1.
 */
public class HobbyNode {

    private String ID;
    private String hobby;
    private String hobbyCategoryID;
    private StudentList studentList;
    private HobbyNode next;

    public HobbyNode(){
        this.ID = this.hobby = this.hobbyCategoryID = null;
        this.studentList = null;
        this.next = null;
    }

    public HobbyNode(String ID,String hobbyCategoryID, String hobby){
        this.ID = ID; this.hobby = hobby; this.hobbyCategoryID = hobbyCategoryID;
        this.studentList = null;
        this.next = null;
    }

    public ServerResponse addStudent(StudentNode stu){
        if (stu == null){
            return ServerResponse.createByErrorMsg("参数错误");
        }
        if(studentList == null){
            studentList = new StudentList();
        }
        StudentNode newStu = new StudentNode(stu.getName(), stu.getID());
        return studentList.addStudent(newStu);
    }

    public ServerResponse removeStudent(String ID){
        if (ID == null){
            return ServerResponse.createByErrorMsg("参数错误");
        }
        if (studentList == null){
            return ServerResponse.createByErrorMsg("studentList is null");
        }
        return studentList.delStudent(ID);
    }

    public boolean isStudentExist(String studentID){

        if (studentList == null){
            return false;
        }

        if (this.studentList.getHead() == null){
            return false;
        }
        StudentNode temp = this.studentList.getHead();
        while (temp.getNext() != null){
            if (temp.getNext().getID().equals(studentID)){
                return true;
            }
            temp = temp.getNext();
        }
        return false;
    }
    
    public ServerResponse modifyHobbyInfo(HobbyNode newHobby){
        if (newHobby == null){
            return ServerResponse.createByErrorMsg("参数错误！");
        }
        hobby = newHobby.getHobby();
        hobbyCategoryID = newHobby.getHobbyCategoryID();
        return ServerResponse.createBySuccessMsg("修改成功！");
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public StudentList getList() {
        return studentList;
    }

    public void setList(StudentList list) {
        this.studentList = list;
    }

    public String getHobbyCategoryID() {
        return hobbyCategoryID;
    }

    public void setHobbyCategoryID(String hobbyCategoryID) {
        this.hobbyCategoryID = hobbyCategoryID;
    }

    public HobbyNode getNext() {
        return next;
    }

    public void setNext(HobbyNode next) {
        this.next = next;
    }

    public StudentList getStudentList() {
        return studentList;
    }

    public void setStudentList(StudentList studentList) {
        this.studentList = studentList;
    }
}
