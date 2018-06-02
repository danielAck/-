package java1.service;
import java1.common.ServerResponse;

/**
 * Created by 张柏桦 on 2018/2/28.
 */
public class StudentList {

    private StudentNode head;
    private StudentNode tail;

    public StudentList(){
        head = new StudentNode();
        tail = null;
    }

    public StudentList(StudentNode stunode){
        this.head.setNext(stunode);
        this.tail = stunode;
    }

    public StudentList(StudentList list){
        this.head = list.head; this.tail = list.tail;
    }

    public StudentNode getHead() {
        return head;
}

    public void setHead(StudentNode head) {
        this.head = head;
    }

    public StudentNode getTail() {
        return tail;
    }

    public void setTail(StudentNode tail) {
        this.tail = tail;
    }

    public ServerResponse addStudent(StudentNode stuNode){
        if (head.getNext() == null){
            this.head.setNext(stuNode);
            this.tail = stuNode;
        } else{
            StudentNode temp = this.head;
            while (temp.getNext() != null){
                if (temp.getNext().getID().equals(stuNode.getID())){
                    return ServerResponse.createByErrorMsg("该学生已存在，请勿重复添加！");
                }
                temp = temp.getNext();
            }
            tail.setNext(stuNode);
            tail = stuNode;
        }
        return ServerResponse.createBySuccessMsg("Add Success!");
    }

    public ServerResponse delStudent(String ID){
        if(this.head.getNext() == null){
            return ServerResponse.createByErrorMsg("Empty List!");
        } else{
            StudentNode temp = this.getHead();
            while (temp.getNext() != null){
                if (temp.getNext().getID().equals(ID)){
                    temp.setNext(temp.getNext().getNext());
                    return ServerResponse.createBySuccessMsg("Delete Success!");
                }
                temp = temp.getNext();
            }
            return ServerResponse.createByErrorMsg("There is no such student!");
        }
    }

    public ServerResponse modifyStudent(String ID, StudentNode stu){
        if (stu == null){
            return ServerResponse.createByErrorMsg("Student is null!");
        }
        if(this.head.getNext() == null){
            return ServerResponse.createByErrorMsg("Empty List!");
        }
        StudentNode temp = this.head;
        while(temp.getNext() != null){
            if (temp.getNext().getID().equals(ID)){
                if (stu.getName() != null){
                    temp.getNext().setName(stu.getName());
                }
            }
            temp = temp.getNext();
        }
        return ServerResponse.createBySuccessMsg("Modify Student Success!");
    }

    public ServerResponse addHobby(String ID, HobbyNode hobbyNode){
        if (ID == null || hobbyNode == null){
            return ServerResponse.createByErrorMsg("参数错误");
        }
        if(this.head == null){
            return ServerResponse.createByErrorMsg("Empty Student List, can't add hobby");
        }
        StudentNode temp = this.head;
        while (temp.getNext() != null){
            if (temp.getNext().getID().equals(ID)){
                return temp.getNext().addHobby(hobbyNode);
            }
            temp = temp.getNext();
        }
        return ServerResponse.createByErrorMsg("There is no such student");
    }

    public ServerResponse removeHobby(String stuID, String hobbyID){
        if (stuID == null || hobbyID == null){
            return ServerResponse.createByErrorMsg("参数错误");
        }
        if(this.head == null){
            return ServerResponse.createByErrorMsg("Empty Student List, can't remove hobby");
        }
        StudentNode temp = this.head;
        while (temp.getNext() != null){
            if (temp.getNext().getID().equals(stuID)){
                temp.getNext().removeHobby(hobbyID);
            }
            temp = temp.getNext();
        }
        return ServerResponse.createByErrorMsg("There is no such student");
    }
}
