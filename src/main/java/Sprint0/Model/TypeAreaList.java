package Sprint0.Model;

import java.util.ArrayList;
import java.util.List;

public class TypeAreaList {
    private List<TypeArea> mTypeAreaList;

    public TypeAreaList() {
        mTypeAreaList = new ArrayList<>();
    }


    public String printTypeAreaList() {
        String finalString = "\nArea Types List:\n";
        if(mTypeAreaList == null || mTypeAreaList.isEmpty()) {
            finalString = finalString + "\n|||| List is Empty ||||\nAdd types to list first";
        }
        else { for (TypeArea tipo : mTypeAreaList)
            finalString = finalString + "\n" + "-" + tipo.getTypeOfGeographicArea() + ";";}
        return finalString;
    }


    public boolean newTAG(String name) {

        if (name == null || name.isEmpty() || name.matches(".*\\d+.*")) {
            return false;
        }
        TypeArea tipo = new TypeArea(name);
        return addTypeArea(tipo);
    }

    public boolean addTypeArea(TypeArea type) {
        if (!mTypeAreaList.contains(type)){
            mTypeAreaList.add(type);
            return true;
        }
        else{
            return false;
        }
    }

    public List<TypeArea> getmTypeAreaList() {
        return this.mTypeAreaList;
    }
}
