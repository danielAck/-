package java1.service;
import java.io.*;
import java.util.List;
import java.util.Set;
import java1.service.*;
/**
 * Created by 张柏桦 on 2018/3/6.
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

        manageer.addHobbyCategory(hobbyCategoryMap, new HobbyCategoryNode("3", "棋类"));
        manageer.studentAddHobby(stuMap, hobbyMap, "3011", "3");
        manageer.addStudent(stuMap, new StudentNode("Romio", "7775"));
        manageer.addHobby(hobbyCategoryMap, hobbyMap, new HobbyNode("4", "3", "围棋"));

        ioService.writeHobbyCategory(hobbyCategoryPath, hobbyCategoryMap);
        ioService.writeHobby(hobbyInfoPath, hobbyMap);
        ioService.writeStudentInfo(studentInfoPath, stuMap);
        ioService.writeStudentHobby(studentHobbyPath, stuMap);


        System.out.println("=============  兴趣类别列表  ==============");
        List<HobbyCategoryNode> hobbyCategorys = (List<HobbyCategoryNode>) manageer.getHobbyCategory(hobbyCategoryMap).getData();
        for (HobbyCategoryNode hobbyCategory : hobbyCategorys){
            System.out.println(hobbyCategory.getHobbyCategory() + " " + hobbyCategory.getID());
            if (hobbyCategory.getHobbyList() == null){
                System.out.println();
            } else{
                HobbyNode temp = hobbyCategory.getHobbyList().getHead();
                System.out.println("\t" + "兴趣列表：");
                while (temp.getNext() != null){
                    System.out.println("\t\t" + temp.getNext().getHobby());
                    temp = temp.getNext();
                }
            }
        }

        System.out.println("=============  兴趣列表  ==============");
        List<HobbyNode> hobbyLists = (List<HobbyNode>) manageer.getHobbyList(hobbyMap).getData();
        for (HobbyNode hobby : hobbyLists){
            System.out.println(hobby.getHobby() + "\t" + hobby.getHobbyCategoryID());
            StudentList stuList = hobby.getStudentList();
            System.out.println("\t" + "兴趣学生列表：");
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

//    从文件读取兴趣类别列表
    public void readHobbyCategory(String fileName, Manageer manageer, HobbyCategoryMap hobbyCategoryMap) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("正在读取兴趣类别列表......");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String[] str = tempString.split(" ");
                String categoryID = str[0];
                String categoryName = str[1];

                manageer.addHobbyCategory(hobbyCategoryMap, new HobbyCategoryNode(categoryID, categoryName));
            }
            System.out.println("读取兴趣类别列表结束");
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

//    文件读取兴趣列表
    public void readHobby(String fileName, Manageer manageer, HobbyCategoryMap hobbyCategoryMap, HobbyMap hobbyMap){
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("正在读取兴趣列表......");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String[] str = tempString.split(" ");
                String hobbyID = str[0];
                String hobbyCategoryID = str[2];
                String hobbyName = str[1];
                manageer.addHobby(hobbyCategoryMap, hobbyMap, new HobbyNode(hobbyID, hobbyCategoryID, hobbyName));
                line++;
            }
            System.out.println("读取兴趣类别结束");
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

//    从文件读取学生信息
    public void readStudentInfo(String fileName, StudentMap stuMap){
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("正在读取学生信息列表......");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                String[] str = tempString.split(" ");
                StudentNode stu = new StudentNode(str[1], str[0], str[3], str[4], str[2]);
                stuMap.put(str[0], stu);
                line++;
            }
            System.out.println("读取学生信息列表结束");
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

//    从文件读取学生兴趣列表
    public void readStudentHobby(String  fileName, Manageer manageer, HobbyMap hobbyMap, StudentMap stuMap){
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("正在读取学生兴趣列表......");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {

                String[] str = tempString.split(" ");
                String stuID = str[0];
                String hobbyID = str[1];

                if (stuMap.isStuIsExist(stuID)){
                    manageer.studentAddHobby(stuMap, hobbyMap, stuID, hobbyID);
                } else {
//                    TODO 处理读取学号失败
                    return ;
                }
                line++;
            }

            System.out.println("读取学生兴趣列表结束");
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

//    将兴趣类别列表内容写入文件
    public void writeHobbyCategory(String fileName, HobbyCategoryMap hobbyCategoryMap){
        System.out.println("正在写入兴趣类别列表......");
        String content = "";
        Set<String> set = hobbyCategoryMap.getKeyset();
        for (String ID : set){
            HobbyCategoryNode hobbyCategoryNode = hobbyCategoryMap.get(ID);
            String line = hobbyCategoryNode.getID() + " " + hobbyCategoryNode.getHobbyCategory();
            content = content + line + "\r\n";
        }
        write(fileName, content);
        System.out.println("写入兴趣类别列表完成");
    }

//    将兴趣列表写入文件中
    public void writeHobby(String fileName, HobbyMap hobbyMap){
        System.out.println("正在写入兴趣列表......");
        String content = "";
        Set<String> set = hobbyMap.getKeyset();
        for (String ID : set){
            HobbyNode hobbyNode = hobbyMap.get(ID);
            String line = hobbyNode.getID() + " " + hobbyNode.getHobby() + " " + hobbyNode.getHobbyCategoryID();
            content = content + line + "\r\n";
        }
        write(fileName, content);
        System.out.println("写入兴趣列表完成");
    }

//    将学生信息写入文件
    public void writeStudentInfo(String fileName, StudentMap stuMap){
        System.out.println("正在写入学生信息......");
        String content = "";
        Set<String> set = stuMap.getKeyset();
        for (String ID : set){
            StudentNode stu = stuMap.get(ID);
            String line = stu.getID() + " " + stu.getName() + " " + stu.getSex() + " " + stu.getGrade() + " " + stu.getMajor();
            content = content + line + "\r\n";
        }
        write(fileName, content);
        System.out.println("写入学生信息完成");
    }

//    将学生喜好兴趣写入文件
    public void writeStudentHobby(String fileName, StudentMap stuMap){
        System.out.println("正在写入学生兴趣列表......");
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
        System.out.println("学生兴趣列表写入完成");
    }


    private void write(String fileName, String content){

        File f=new File(fileName);//新建一个文件对象，如果不存在则创建一个该文件
        FileWriter fw;
        try {
            fw=new FileWriter(f);
            fw.write(content);//将字符串写入到指定的路径下的文件中
            fw.close();
        } catch (IOException e) { e.printStackTrace(); }
    }



}
