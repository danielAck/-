package java1.service;
import java.io.*;
import java.util.List;
import java.util.Set;
import java1.service.*;
/**
 * Created by �Ű��� on 2018/3/6.
 */
public class IOService {

    public static void main(String[] args) {
    	IOService ioService = new IOService();
        Manageer manageer = new Manageer();
        HobbyCategoryMap hobbyCategoryMap = new HobbyCategoryMap();
        StudentMap stuMap = new StudentMap();
        HobbyMap hobbyMap = new HobbyMap();

        String hobbyCategoryPath = "D:\\FC\\HobbyCategoryInfo.txt";
        String hobbyInfoPath = "D:\\FC\\HobbyInfo.txt";
        String studentInfoPath = "D:\\FC\\StudentInfo.txt";
        String studentHobbyPath = "D:\\FC\\StudentHobby.txt";

        ioService.readHobbyCategory(hobbyCategoryPath, manageer, hobbyCategoryMap);
        ioService.readHobby(hobbyInfoPath, manageer, hobbyCategoryMap, hobbyMap);
        ioService.readStudentInfo(studentInfoPath, stuMap);
        ioService.readStudentHobby(studentHobbyPath, manageer, hobbyMap, stuMap);

        manageer.addHobbyCategory(hobbyCategoryMap, new HobbyCategoryNode("3", "����"));
        manageer.studentAddHobby(stuMap, hobbyMap, "3011", "3");
        manageer.addStudent(stuMap, new StudentNode("Romio", "7775"));
        manageer.addHobby(hobbyCategoryMap, hobbyMap, new HobbyNode("4", "3", "Χ��"));

        ioService.writeHobbyCategory(hobbyCategoryPath, hobbyCategoryMap);
        ioService.writeHobby(hobbyInfoPath, hobbyMap);
        ioService.writeStudentInfo(studentInfoPath, stuMap);
        ioService.writeStudentHobby(studentHobbyPath, stuMap);


        System.out.println("=============  ��Ȥ����б�  ==============");
        List<HobbyCategoryNode> hobbyCategorys = (List<HobbyCategoryNode>) manageer.getHobbyCategory(hobbyCategoryMap).getData();
        for (HobbyCategoryNode hobbyCategory : hobbyCategorys){
            System.out.println(hobbyCategory.getHobbyCategory() + " " + hobbyCategory.getID());
            if (hobbyCategory.getHobbyList() == null){
                System.out.println();
            } else{
                HobbyNode temp = hobbyCategory.getHobbyList().getHead();
                System.out.println("\t" + "��Ȥ�б�");
                while (temp.getNext() != null){
                    System.out.println("\t\t" + temp.getNext().getHobby());
                    temp = temp.getNext();
                }
            }
        }

        System.out.println("=============  ��Ȥ�б�  ==============");
        List<HobbyNode> hobbyLists = (List<HobbyNode>) manageer.getHobbyList(hobbyMap).getData();
        for (HobbyNode hobby : hobbyLists){
            System.out.println(hobby.getHobby() + "\t" + hobby.getHobbyCategoryID());
            StudentList stuList = hobby.getStudentList();
            System.out.println("\t" + "��Ȥѧ���б�");
            if (stuList == null){
                System.out.println();
            } else {
                StudentNode temp = stuList.getHead();
                while (temp.getNext() != null){
                    System.out.println("\t\t" + temp.getNext().getName());
                    temp = temp.getNext();
                }
            }
        }

    }

//    ���ļ���ȡ��Ȥ����б�
    public void readHobbyCategory(String fileName, Manageer manageer, HobbyCategoryMap hobbyCategoryMap) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("���ڶ�ȡ��Ȥ����б�......");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
                String[] str = tempString.split(" ");
                String categoryID = str[0];
                String categoryName = str[1];

                manageer.addHobbyCategory(hobbyCategoryMap, new HobbyCategoryNode(categoryID, categoryName));
            }
            System.out.println("��ȡ��Ȥ����б����");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

//    �ļ���ȡ��Ȥ�б�
    public void readHobby(String fileName, Manageer manageer, HobbyCategoryMap hobbyCategoryMap, HobbyMap hobbyMap){
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("���ڶ�ȡ��Ȥ�б�......");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
                String[] str = tempString.split(" ");
                String hobbyID = str[0];
                String hobbyCategoryID = str[2];
                String hobbyName = str[1];
                manageer.addHobby(hobbyCategoryMap, hobbyMap, new HobbyNode(hobbyID, hobbyCategoryID, hobbyName));
                line++;
            }
            System.out.println("��ȡ��Ȥ������");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

//    ���ļ���ȡѧ����Ϣ
    public void readStudentInfo(String fileName, StudentMap stuMap){
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("���ڶ�ȡѧ����Ϣ�б�......");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
                String[] str = tempString.split(" ");
                StudentNode stu = new StudentNode(str[1], str[0], str[3], str[4], str[2]);
                stuMap.put(str[0], stu);
                line++;
            }
            System.out.println("��ȡѧ����Ϣ�б����");
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

//    ���ļ���ȡѧ����Ȥ�б�
    public void readStudentHobby(String  fileName, Manageer manageer, HobbyMap hobbyMap, StudentMap stuMap){
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("���ڶ�ȡѧ����Ȥ�б�......");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {

                String[] str = tempString.split(" ");
                String stuID = str[0];
                String hobbyID = str[1];

                if (stuMap.isStuIsExist(stuID)){
                    manageer.studentAddHobby(stuMap, hobbyMap, stuID, hobbyID);
                } else {
//                    TODO �����ȡѧ��ʧ��
                    return ;
                }
                line++;
            }

            System.out.println("��ȡѧ����Ȥ�б����");
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }

//    ����Ȥ����б�����д���ļ�
    public void writeHobbyCategory(String fileName, HobbyCategoryMap hobbyCategoryMap){
        System.out.println("����д����Ȥ����б�......");
        String content = "";
        Set<String> set = hobbyCategoryMap.getKeyset();
        for (String ID : set){
            HobbyCategoryNode hobbyCategoryNode = hobbyCategoryMap.get(ID);
            String line = hobbyCategoryNode.getID() + " " + hobbyCategoryNode.getHobbyCategory();
            content = content + line + "\r\n";
        }
        write(fileName, content);
        System.out.println("д����Ȥ����б����");
    }

//    ����Ȥ�б�д���ļ���
    public void writeHobby(String fileName, HobbyMap hobbyMap){
        System.out.println("����д����Ȥ�б�......");
        String content = "";
        Set<String> set = hobbyMap.getKeyset();
        for (String ID : set){
            HobbyNode hobbyNode = hobbyMap.get(ID);
            String line = hobbyNode.getID() + " " + hobbyNode.getHobby() + " " + hobbyNode.getHobbyCategoryID();
            content = content + line + "\r\n";
        }
        write(fileName, content);
        System.out.println("д����Ȥ�б����");
    }

//    ��ѧ����Ϣд���ļ�
    public void writeStudentInfo(String fileName, StudentMap stuMap){
        System.out.println("����д��ѧ����Ϣ......");
        String content = "";
        Set<String> set = stuMap.getKeyset();
        for (String ID : set){
            StudentNode stu = stuMap.get(ID);
            String line = stu.getID() + " " + stu.getName() + " " + stu.getSex() + " " + stu.getGrade() + " " + stu.getMajor();
            content = content + line + "\r\n";
        }
        write(fileName, content);
        System.out.println("д��ѧ����Ϣ���");
    }

//    ��ѧ��ϲ����Ȥд���ļ�
    public void writeStudentHobby(String fileName, StudentMap stuMap){
        System.out.println("����д��ѧ����Ȥ�б�......");
        String content = "";
        Set<String> set = stuMap.getKeyset();
        for (String ID : set){
            StudentNode stu = stuMap.get(ID);
            HobbyList list = stu.getHobbyList();
            if (list == null || list.getHead() == null){
                content += "";
            } else {
                HobbyNode temp =  list.getHead();
                while (temp.getNext() != null){
                    String line = stu.getID() + " " + temp.getNext().getID();
                    content = content + line + "\r\n";
                    temp = temp.getNext();
                }
            }
        }
        write(fileName, content);
        System.out.println("ѧ����Ȥ�б�д�����");
    }


    private void write(String fileName, String content){

        File f=new File(fileName);//�½�һ���ļ���������������򴴽�һ�����ļ�
        FileWriter fw;
        try {
            fw=new FileWriter(f);
            fw.write(content);//���ַ���д�뵽ָ����·���µ��ļ���
            fw.close();
        } catch (IOException e) { e.printStackTrace(); }
    }



}
