package com.Swimer.LIX;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class LIXStream {

    private final File path;
    private boolean fromFile;

    private ArrayList<String> content;
    public LIXStream(File path) throws FileNotFoundException {
        this.fromFile = true;
        this.path = path;
        content = new ArrayList<>();
        Scanner scanner = new Scanner(this.path);
        String line = "";
        while (scanner.hasNext()){
            line=scanner.nextLine();
            content.add(line);
        }
    }
    public LIXStream(String lixContent) {
        this.fromFile = false;
        this.path = null;
        content = new ArrayList<>();
        content.addAll(Arrays.asList(lixContent.split("\n")));
    }
    @Override
    public String toString(){
        String output = "";
        for (String str : this.content){
            if(output.isEmpty()){output = str;}else {output += "\n"+str;}
        }
        return output;
    }
    public Object get(String path){
        Map<String,Object> datas = new HashMap<>();
        String lastArrayCreater = "";
        String lastStringArrayC = "";
        ArrayList<String> lastArray = new ArrayList<>();
        for (String str : content){
            if(!str.isEmpty() && !str.startsWith(";") && !str.startsWith("{*") && !str.startsWith("**") && !str.startsWith("*")){
                String[] strs = str.split("=");
                if(str.endsWith("=:")){
                    lastArrayCreater = strs[0];
                    continue;
                }
                if(str.endsWith("=|")){
                    lastStringArrayC = strs[0];
                    lastArray = new ArrayList<>();
                    continue;
                }
                if(str.startsWith("  - ")){
                    if(lastStringArrayC.isEmpty()) return null;
                    lastArray.add(stringer(str.substring(4)));
                    continue;
                }
                if(!str.startsWith("  - ") && !str.startsWith("   - ") && !lastArray.equals(new ArrayList<String>())){
                    datas.put(lastStringArrayC, lastArray);
                    lastArray = new ArrayList<>();
                }

                if(str.startsWith(" ") && !str.startsWith("  - ") && !str.startsWith("   - ") && !lastArrayCreater.isEmpty()){
                    if(strs.length == 2){
                        datas.put(lastArrayCreater+"."+strs[0].replace(" ", ""), stringer(strs[1]));
                    }
                    continue;
                }
                if(!str.startsWith(" ") && !lastArrayCreater.isEmpty() && !str.startsWith("  - ") && !str.startsWith("   - ")) lastArrayCreater="";

                if(strs.length == 2){
                    datas.put(strs[0], stringer(strs[1]));
                }
            }
        }
        if(!datas.containsKey(path)){
            return null;
        }
        return datas.get(path);
    }
    private String stringer(String input){
        String out = "";
        char[] chars = input.toCharArray();
        if(chars[0]!='\"'){return input;}
        if(chars[chars.length-1]!='\"'){return input;}
        boolean inComment =false;
        for (int i = 1; i < chars.length-1; i++) {
            if(!inComment){
                if(chars[i]=='{'){
                    if(chars[i+1]=='*'){
                        inComment=true;
                        continue;
                    }
                }
            }
            if(inComment){
                if(chars[i-1]=='*'){
                    if(chars[i]=='}'){
                        inComment=false;
                        continue;
                    }
                }
                continue;
            }

            out += chars[i];
        }
        return out;
    }
    public String getString(String path){
        return (String) get(path);
    }
    public ArrayList<String> getStringList(String path){
        return (ArrayList<String>) get(path);
    }
    public int getInt(String path){
        return Integer.parseInt((String) get(path));
    }
    public double getDouble(String path){
        return Double.parseDouble((String) get(path));
    }
    public float getFloat(String path){
        return Float.parseFloat((String) get(path));
    }
    public boolean getBoolean(String path){
        return Boolean.parseBoolean((String) get(path));
    }
    public short getShort(String path){
        return Short.parseShort((String) get(path));
    }
    public long getLong(String path){
        return Long.parseLong((String) get(path));
    }
    public byte getByte(String path){
        return Byte.parseByte((String) get(path));
    }
    public void reload() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(this.path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = "";
        while (true){
            assert scanner != null;
            if (!scanner.hasNext()) break;
            line=scanner.nextLine();
            content.add(line);
        }
    }
    public void set(String path, Object valuee){
        for (int i = 0; i < content.size(); i++) {
            if(content.get(i).startsWith(path+"=")){
                String value = valuee instanceof String ? "\""+valuee+"\"" : valuee.toString();
                content.set(i, path+"="+value);
            }
        }
    }
    public void save() throws IOException {
        if(this.fromFile){
            FileOutputStream fos = new FileOutputStream(this.path);
            fos.write(this.toString().getBytes(StandardCharsets.UTF_8));
        }

    }
}
