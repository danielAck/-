package java1.service;
import java1.common.ServerResponse;

/**
 * Created by �Ű��� on 2018/3/1.
 */
public class HobbyList {

    private HobbyNode head;
    private HobbyNode tail;

    public HobbyList(){
        head = new HobbyNode();
        tail = null;
    }

    public HobbyList(HobbyNode hobbyNode){
        this.head.setNext(hobbyNode);
        this.tail = hobbyNode;
    }

    public HobbyList(HobbyList list){
        this.head = list.head; this.tail = list.tail;
    }

    public HobbyNode getHead() {
        return head;
    }

    public void setHead(HobbyNode head) {
        this.head = head;
    }

    public HobbyNode getTail() {
        return tail;
    }

    public void setTail(HobbyNode tail) {
        this.tail = tail;
    }

    public ServerResponse addHobby(HobbyNode hobby){
        if (head.getNext() == null){
            this.head.setNext(hobby);
            this.tail = hobby;
        } else{
            HobbyNode temp = this.head;
            while (temp.getNext() != null){
                if (temp.getNext().getID().equals(hobby.getID())){
                    return ServerResponse.createByErrorMsg("����Ȥ�Ѵ��ڣ������ظ���ӣ�");
                }
                temp = temp.getNext();
            }
            tail.setNext(hobby);
            tail = hobby;
        }
        return ServerResponse.createBySuccessMsg("Add Success!");
    }

    public ServerResponse delHobby(String ID){
        if(this.head.getNext() == null){
            return ServerResponse.createByErrorMsg("Empty List!");
        } else{
            HobbyNode temp = this.getHead();
            while (temp.getNext() != null){
                if (temp.getNext().getID().equals(ID)){
                    temp.setNext(temp.getNext().getNext());
                    return ServerResponse.createBySuccessMsg("Delete Success!");
                }
                temp = temp.getNext();
            }
            return ServerResponse.createBySuccess();
        }
    }

    public ServerResponse modifyHobby(String ID, HobbyNode hobby){
        if (hobby == null){
            return ServerResponse.createByErrorMsg("Student is null!");
        }
        if(this.head.getNext() == null){
            return ServerResponse.createByErrorMsg("Empty List!");
        }
        HobbyNode temp = this.head;
        while(temp.getNext() != null){
            if (temp.getNext().getID().equals(ID)){
//                �޸���Ȥ����
                if (hobby.getHobby() != null){
                    temp.getNext().setHobby(hobby.getHobby());
                }
//                �޸���Ȥ�������
                if(hobby.getHobbyCategoryID() != null){
                    temp.getNext().setHobbyCategoryID(hobby.getHobbyCategoryID());
                }
                break;
            }
        }
        return ServerResponse.createBySuccessMsg("�޸ĳɹ�!");
    }

    public ServerResponse addStudent(String hobbyID, StudentNode stu){
        if (stu == null || hobbyID ==null){
            return ServerResponse.createByErrorMsg("��������");
        }
        if (this.head == null){
            return ServerResponse.createByErrorMsg("Empty Hobby List, can't add student!");
        }
        HobbyNode temp = this.head;
        while (temp.getNext() != null){
            if (temp.getNext().getID().equals(hobbyID)){
                return temp.getNext().addStudent(stu);
            }
        }
        return ServerResponse.createByErrorMsg("There is no such hobby");
    }

    public ServerResponse removeStudent(String hobbyID, String studentID){
        if (hobbyID == null || studentID == null){
            return ServerResponse.createByErrorMsg("��������");
        }
        HobbyNode temp = this.head;
        while (temp.getNext() != null){
            if (temp.getNext().getID().equals(hobbyID)){
                return temp.getNext().removeStudent(studentID);
            }
        }
        return ServerResponse.createByErrorMsg("There is no such hobby");
    }
}
