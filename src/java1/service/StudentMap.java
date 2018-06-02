package java1.service;
import java.util.HashSet;
import java.util.Set;
import java1.common.ServerResponse;
/**
 * Created by �Ű��� on 2018/3/1.
 */
public class StudentMap {

    private final Integer capacity = 200;
    private StudentList[] table;
    private Set<String> keyset;
    private int[] inint;
    
    public StudentMap(){
    	table = new StudentList[capacity];
    	keyset = new HashSet();
    	inint = new int[200];
    }

    public StudentNode get(String key){

        if (keyset.isEmpty()){
            return null;
        }

        Integer hashcode = hash(key);
        StudentList list = table[hashcode];

        StudentNode temp = list.getHead();
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

    public ServerResponse put(String key, StudentNode value){

        if (keyset.contains(key)){
            return ServerResponse.createByErrorMsg("��ѧ���Ѵ��ڣ������ظ����");
        }

        Integer hashcode = hash(key);
        if(inint[hashcode] == 0){
            table[hashcode] = new StudentList();
            inint[hashcode] = 1;
        }
        StudentList list = table[hashcode];
        if (list.getHead().getNext() == null){
            list.getHead().setNext(value);
        } else {
            list.getTail().setNext(value);
            list.setTail(value);
        }
        keyset.add(key);

        return ServerResponse.createBySuccessMsg("��ӳɹ���");
    }

    public ServerResponse removeStu(String key){
        if (!keyset.contains(key)){
            return ServerResponse.createByErrorMsg("��Ǹ����ѧ��������");
        }
        Integer hashcode = hash(key);
        StudentList list = table[hashcode];
        list.delStudent(key);
        keyset.remove(key);
        return ServerResponse.createBySuccessMsg("ɾ���ɹ���");
    }

    public boolean isStuIsExist(String stuID){
        return keyset.contains(stuID);
    }

    public Set<String> getKeyset(){
        return keyset;
    }

    private Integer hash(String key){
        return key.hashCode() % capacity;
    }


}
