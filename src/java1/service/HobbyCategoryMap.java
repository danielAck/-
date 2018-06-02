package java1.service;
import java.util.HashSet;
import java.util.Set;
import java1.common.ServerResponse;

/**
 * Created by 张柏桦 on 2018/3/1.
 */
public class HobbyCategoryMap {

    private final Integer capacity = 200;
    private HobbyCategoryList[] table;
    private Set<String> keyset;
    private int[] inint;
    
    public HobbyCategoryMap(){
    	table = new  HobbyCategoryList[capacity];
    	keyset = new HashSet();
    	inint = new int[200];
    }

    public HobbyCategoryNode get(String key){

        if (keyset.isEmpty()){
            return null;
        }

        Integer hashcode = hash(key);
        HobbyCategoryList list = table[hashcode];

        HobbyCategoryNode temp = list.getHead();
        if (temp.getNext() == null){
            return null;
        }
        while (temp.getNext() != null){
            if (temp.getNext().getID().equals(key)){
                return temp.getNext();
            }
            temp = temp.getNext();
        }
        return temp;
    }

    public ServerResponse put(String key, HobbyCategoryNode value){

        if (keyset.contains(key)){
            return ServerResponse.createByErrorMsg("该兴趣类别已存在，请勿重复添加！");
        }

        Integer hashcode = hash(key);
        if(inint[hashcode] == 0){
            table[hashcode] = new HobbyCategoryList();
            inint[hashcode] = 1;
        }
        HobbyCategoryList list = table[hashcode];
        if (list.getHead().getNext() == null){
            list.getHead().setNext(value);
            list.setTail(value);
        } else {
            list.getTail().setNext(value);
            list.setTail(value);
        }
        keyset.add(key);

        return ServerResponse.createBySuccessMsg("添加兴趣类别成功！");
    }

    public ServerResponse removeHobbyCategory(String key){
        if (!keyset.contains(key)){
            return ServerResponse.createBySuccess();
        }
        Integer hashcode = hash(key);
        HobbyCategoryList list = table[hashcode];
        list.delHobbyCategory(key);
        keyset.remove(key);
        return ServerResponse.createBySuccessMsg("删除成功！");
    }

    public Set<String> getKeyset(){
        return keyset;
    }

    private Integer hash(String key){
        return key.hashCode() % capacity;
    }
}
