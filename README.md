# Java
### Methods:
#### Init:
- `LIXStream lix = new LIXStream(` `new File(path)` or `"howMuch=5"` `);`
#### Get values:
- `lix.get("String");` returns Object (String)
- `lix.getString("SimpleLine");` returns String (rfgrtg)
- `lix.getStringList("StringList");` returns ArratList\<String\>()   ["test!", "tEST!"]
- `lix.getString("ArrayList.SubElement");` returns String (fgnhyh)
- `lix.getInt("Integer");` returns int (5)
- `lix.set("SimpleLine", "New Line");` the method returns nothing, but sets values for a specific key
- `lix.save();` the method returns nothing, but saves all changes to the file specified in the constructor
- `lix.reload();` the method returns nothing, but loads the values and keys from the file again

#### LIX File (Using in examples):

    ; section @tutorialSection 
    StringList=|
      - "test!"
      - "tEST!"
    Integer=5
    ArrayList=:
     SubElement="fgnhyh"
    SimpleLine="rfgrtg"
    ; end_section
 Sections do not affect anything, they are for readability
 
