package java1.service;
import java1.common.ServerResponse;

/**
 * Created by 张柏桦 on 2018/3/1.
 */
public class HobbyCategoryNode {

    private String ID;
    private String hobbyCategory;
    private HobbyList hobbyList;
    private HobbyCategoryNode next;

    public HobbyCategoryNode(){
        this.ID = this.hobbyCategory = null;
        this.hobbyList = null;
        this.next = null;
    }

    public HobbyCategoryNode(String ID,String hobbyCategory){
        this.ID = ID; this.hobbyCategory = hobbyCategory;
        this.hobbyList = null;
        this.next = null;
    }

    public ServerResponse addHobby(HobbyNode hobby){
        if (hobby == null){
            return ServerResponse.createByErrorMsg("hobby witch add to is null!");
        }
        if (hobbyList == null){
            hobbyList = new HobbyList();
        }
        HobbyNode newHobby = new HobbyNode(hobby.getID(), hobby.getHobbyCategoryID(), hobby.getHobby());
        return hobbyList.addHobby(newHobby);
    }

    public ServerResponse removeHobby(String ID){
        if(ID == null){
            return ServerResponse.createByErrorMsg("hobby ID is null!");
        }
        if (hobbyList == null){
            return ServerResponse.createByErrorMsg("hobbyList is null");
        }
        return hobbyList.delHobby(ID);
    }
    
    public ServerResponse modifyHobby(String hobbyID, HobbyNode newHobby){
        if (hobbyID == null || newHobby == null){
            return ServerResponse.createByErrorMsg("参数错误!");
        }
        HobbyNode temp = hobbyList.getHead();
        while (temp.getNext() != null){
            if (temp.getNext().getID().equals(hobbyID)){
                temp.getNext().setHobby(newHobby.getHobby());
                return ServerResponse.createBySuccessMsg("修改成功！");
            }
            temp = temp.getNext();
        }
        return ServerResponse.createByErrorMsg("该兴趣类别没有该兴趣！");
    }
    
    public ServerResponse modifyHobbyCategory(HobbyCategoryNode newHobbyCategory){
        if (newHobbyCategory == null){
            return ServerResponse.createByErrorMsg("参数错误！");
        }
        hobbyCategory = newHobbyCategory.getHobbyCategory();
        return ServerResponse.createBySuccessMsg("修改成功!");
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getHobbyCategory() {
        return hobbyCategory;
    }

    public void setHobbyCategory(String hobbyCategory) {
        this.hobbyCategory = hobbyCategory;
    }

    public HobbyList getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(HobbyList hobbyList) {
        this.hobbyList = hobbyList;
    }

    public HobbyCategoryNode getNext() {
        return next;
    }

    public void setNext(HobbyCategoryNode next) {
        this.next = next;
    }
}
