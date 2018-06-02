package java1.service;
import java1.common.ServerResponse;

/**
 * Created by 张柏桦 on 2018/3/1.
 */
public class HobbyCategoryList {

    private HobbyCategoryNode head;
    private HobbyCategoryNode tail;

    public HobbyCategoryList(){
        head = new HobbyCategoryNode();
        tail = null;
    }

    public HobbyCategoryList(HobbyCategoryNode hobbyCategoryNode){
        this.head.setNext(hobbyCategoryNode);
        this.tail = hobbyCategoryNode;
    }

    public HobbyCategoryList(HobbyCategoryList list){
        this.head = list.head; this.tail = list.tail;
    }

    public ServerResponse addHobbyCategory(HobbyCategoryNode hobbyCategoryNode){
        if (head.getNext() == null){
            this.head.setNext(hobbyCategoryNode);
            this.tail = hobbyCategoryNode;
        } else{
            tail.setNext(hobbyCategoryNode);
            tail = hobbyCategoryNode;
        }
        return ServerResponse.createBySuccessMsg("Add Success!");
    }

    public ServerResponse delHobbyCategory(String ID){
        if(this.head.getNext() == null){
            return ServerResponse.createByErrorMsg("Empty List!");
        } else{
            HobbyCategoryNode temp = this.getHead();
            while (temp.getNext() != null){
                if (temp.getNext().getID().equals(ID)){
                    temp.setNext(temp.getNext().getNext());
                    return ServerResponse.createBySuccessMsg("Delete Success!");
                }
                temp = temp.getNext();
            }
            return ServerResponse.createByErrorMsg("There is no such hobbyCategory!");
        }
    }

    public ServerResponse modifyHobbyCategory(String ID, HobbyCategoryNode hobbyCategoryNode){
        if (hobbyCategoryNode == null){
            return ServerResponse.createByErrorMsg("Student is null!");
        }
        if(this.head.getNext() == null){
            return ServerResponse.createByErrorMsg("Empty List!");
        }
        HobbyCategoryNode temp = this.head;
        while(temp.getNext() != null){
            if (temp.getNext().getID().equals(ID)){
                if (hobbyCategoryNode.getHobbyCategory() != null){
                    temp.getNext().setHobbyCategory(hobbyCategoryNode.getHobbyCategory());
                }
                break;
            }
            temp = temp.getNext();
        }
        return ServerResponse.createBySuccessMsg("Modify hobbyCategoryList Success!");
    }

    public ServerResponse addHobby(String categoryID, HobbyNode hobby){
        if (categoryID == null || hobby == null){
            return ServerResponse.createByErrorMsg("参数错误!");
        }
        HobbyCategoryNode temp = this.head;
        while (temp.getNext() != null){
            if (temp.getID().equals(categoryID)){
                return temp.addHobby(hobby);
            }
            temp = temp.getNext();
        }
        return ServerResponse.createByErrorMsg("There is no such hobbycategory");
    }

    public ServerResponse delHobby(String categoryID, String hobbyID){
        if (categoryID == null || hobbyID == null){
            return ServerResponse.createByErrorMsg("参数错误!");
        }
        HobbyCategoryNode temp = this.head;
        while (temp.getNext() != null){
            if (temp.getID().equals(categoryID)){
                return temp.removeHobby(hobbyID);
            }
        }
        return ServerResponse.createByErrorMsg("There is no such hobbycategory");
    }

    public HobbyCategoryNode getHead() {
        return head;
    }

    public void setHead(HobbyCategoryNode head) {
        this.head = head;
    }

    public HobbyCategoryNode getTail() {
        return tail;
    }

    public void setTail(HobbyCategoryNode tail) {
        this.tail = tail;
    }
}
