package es.uva.locomotion.model;


import java.util.*;
import java.util.stream.Collectors;

public class Symbol {


    private final String token;
    private final List<Integer> linesDefined;
    private final List<List<Symbol>> indexes;
    private String units;
    private String comment;
    private Set<Symbol> dependencies;
    private SymbolType type;
    private String category;
    private String primary_module;
    private final List<String> shadow_module;
    private String group;
    private List<ExcelRef> excel;

    private boolean isValid;
    private Class<?> invalidReason;

    private boolean isFiltered;


    public Symbol(String token){
        this.token = token.strip();
        dependencies = new HashSet<>();
        type = SymbolType.UNDETERMINED;
        linesDefined = new ArrayList<>();
        units = "";
        comment ="";
        category = "";
        indexes = new ArrayList<>();
        isValid = true;
        primary_module = "";
        shadow_module = new ArrayList<>();
        group = "";
        excel = new ArrayList<>();

    }


    public Symbol(String token, SymbolType type){
      this(token);
      this.type = type;
    }

    public void addDefinitionLine(int line){
        linesDefined.add(line);
    }

    public void setType(SymbolType type) {
        this.type = type;
    }


    public List<Integer> getDefinitionLines(){
        return linesDefined;
    }

    /*
     * @return Dependencies of the symbol. For example in "a = b*func(c)" it would return 'b', 'c' and 'func'.
     * Subscripts also contain subscript values as dependencies.
     */
    public Set<Symbol> getDependencies() {
        return dependencies;
    }

    public SymbolType getType() {
        return type;
    }

    public void addDependency(Symbol symbol){
        dependencies.add(symbol);

    }

    public void addDependencies(Collection<Symbol> symbols){
        for(Symbol symb: symbols){
            addDependency(symb);
        }
    }



    public String getToken() {
        return token;
    }


    public boolean hasType(){
        return getType()!=SymbolType.UNDETERMINED;
    }

    public void addIndexLine(List<Symbol> indexLine){

        int sizeDiff = indexLine.size() - indexes.size();
        if(sizeDiff>0)
            for(int i=0;i<sizeDiff;i++)
                indexes.add(new ArrayList<>());


        for(int i = 0; i<indexLine.size();i++){
            Symbol index = indexLine.get(i);
            indexes.get(i).add(index);
        }


    }



    public List<List<Symbol>> getIndexes(){
        return indexes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol)) return false;
        Symbol symbol = (Symbol) o;
        return getToken().equals(symbol.getToken()) &&
                linesDefined.equals(symbol.linesDefined) &&
                getIndexes().equals(symbol.getIndexes()) &&
                getUnits().equals(symbol.getUnits()) &&
                getComment().equals(symbol.getComment()) &&
                getDependencies().equals(symbol.getDependencies()) &&
                getType() == symbol.getType() &&
                getCategory().equals(symbol.getCategory()) &&
                getPrimary_module().equals(symbol.getPrimary_module()) &&
                getShadow_module().equals(symbol.getShadow_module());
    }

    public boolean dbEquals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Symbol)) return false;
        Symbol symbol = (Symbol) o;
        return getToken().equals(symbol.getToken()) &&
                getIndexes().equals(symbol.getIndexes()) &&
                getUnits().equals(symbol.getUnits()) &&
                getComment().equals(symbol.getComment()) &&
                getType() == symbol.getType() &&
                getCategory().equals(symbol.getCategory()) &&
                getPrimary_module().equals(symbol.getPrimary_module()) &&
                getShadow_module().equals(symbol.getShadow_module());
    }


    @Override
    public String toString() {
        return "Symbol{" +
                "token='" + token + '\'' +
                ", linesDefined=" + linesDefined +
                ", indexes=" + indexes +
                ", units='" + units + '\'' +
                ", comment='" + comment + '\'' +
                ", dependencies=" + dependencies.stream().map(Symbol::getToken).collect(Collectors.toList()) +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", primary_module='" + getPrimary_module() + '\'' +
                ", shadow_module=" + getShadow_module() +
                ", group='" + group + '\'' +
                ", excel=" + excel +
                ", isValid=" + isValid +
                ", invalidReason=" + invalidReason +
                ", isFiltered=" + isFiltered +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken(), linesDefined, getIndexes(), getUnits(), getComment(), getType(), getCategory(), getPrimary_module(), getShadow_module());
    }

    public void setUnits(String units) {
        this.units = units.strip();
    }

    public void setComment(String comment){
        this.comment = comment.strip();
    }

    public String getUnits() {
        return units;
    }

    public String getComment() {
        return comment;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup(){
        return group;
    }

    /**
     * Overrides the dependencies
     * @param dependencies new dependencies
     */
    public void setDependencies(Set<Symbol> dependencies) {
        this.dependencies = dependencies;
    }

    public void setAsInvalid(Class<?> who){
        isValid = false;
        invalidReason = who;
    }

    public boolean isValid() {
        return isValid;
    }
    public String getReasonForInvalid() {
        return invalidReason.getSimpleName();
    }
    public String getPrimary_module() { return primary_module; }

    public void setPrimary_module(String primary_module) { this.primary_module = primary_module; }

    public List<String> getShadow_module() {
        return shadow_module;
    }

    public void addShadow_view(String module){
        shadow_module.add(module.trim());
    }

    public List<String> get_views(){
        List<String> list = new ArrayList<>(getShadow_module());
        list.add(getPrimary_module());
        return list;
    }

    public boolean isFiltered() {
        return isFiltered;
    }

    public void setFiltered(boolean filtered) {
        isFiltered = filtered;
    }

    public List<ExcelRef> getExcel() {
        return excel;
    }


    public void setExcel(List<ExcelRef> excel) {
        this.excel = excel;
    }

    public ExcelRef getExcelOrCreate(String filename, String sheet) {
        ExcelRef excelTmp = new ExcelRef(filename,sheet);
        if (excel.contains(excelTmp))
            return excel.get(excel.indexOf(excelTmp));

        else {
            excel.add(excelTmp);
            return excelTmp;
        }
    }
}
