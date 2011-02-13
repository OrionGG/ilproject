package dominio;

import java.util.ArrayList;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.rdf.model.AnonId;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.RDFVisitor;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

public class DbPediaResource implements Resource{
	private String uri;
	private String name;
	private ArrayList<String> types;
	private AnonId id;
	private String nameSpace;
	private String abs;
	private ArrayList<String> references;
	private String image="img/resource.jpg";
	private String photoCol;
	
	public DbPediaResource(String _path){
		this.uri=_path;
		//uri=this.getURI();
		this.name=this.getLocalName();
		this.id=this.getId();
		this.nameSpace = this.getNameSpace();
	}
	public DbPediaResource(String _name, String _namespace,String _path){
		this.uri=_path;
		this.name=_name;
		this.nameSpace = _namespace;
	}
	public String getUri(){
		
		return uri;
	}
	public String getImage(){
		
		return image;
	}
	public String getName(){
		if(name!=null){
			if(name.endsWith("@en")){
				name=name.replace("@en", "");
			}
			if(name.contains("@")){
				int index=name.indexOf("@");
				int endIndex=name.length();
				name=name.replace(name.substring(index, endIndex),"");	
				
			}
		}
		return name;
		
	}	
	public String getPhotoCol(){
		
		return photoCol;
	}
	public ArrayList<String> getReferences(){
		
		return references;
	}
	public ArrayList<String> getTypes(){
		
		return types;
	}	
	public String getAbs(){
		
		return abs;
	}
	public String getNameSpace(){
		
		return nameSpace;
	}
	public void setUri(String _uri){
		this.uri=_uri;
		return ;
	}
	public void setPhotoCol(String _col){
		this.photoCol=_col;
		return ;
	}
	public void setName(String _name){
		this.name=_name;
		return ;
	}
	public void setNameSpace1(String _nameSpace){
		this.nameSpace=_nameSpace;
		return ;
	}
	public void setImage(String image2) {
		// TODO Auto-generated method stub
		image=image2;
	}
	public void setTypes(ArrayList<String> _types) {
		// TODO Auto-generated method stub
		types=_types;
		return ;
	}
	public void setAbstract(String uri2) {
		// TODO Auto-generated method stub
		abs=uri2;
		
	}
	public void setReferences(ArrayList<String> _references) {
		// TODO Auto-generated method stub
		references=_references;
		
	}
	
	
	@Override
	public <T extends RDFNode> T as(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends RDFNode> boolean canAs(Class<T> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Resource inModel(Model arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAnon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isLiteral() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isResource() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isURIResource() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object visitWith(RDFVisitor arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Node asNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource abort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addLiteral(Property arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addLiteral(Property arg0, long arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addLiteral(Property arg0, char arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addLiteral(Property arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addLiteral(Property arg0, float arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addLiteral(Property arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addLiteral(Property arg0, Literal arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addProperty(Property arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addProperty(Property arg0, RDFNode arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addProperty(Property arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource addProperty(Property arg0, String arg1, RDFDatatype arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource begin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource commit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnonId getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model getModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement getProperty(Property arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement getRequiredProperty(Property arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getURI() {
		// TODO Auto-generated method stub
		return getUri();
	}

	@Override
	public boolean hasLiteral(Property arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasLiteral(Property arg0, long arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasLiteral(Property arg0, char arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasLiteral(Property arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasLiteral(Property arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasLiteral(Property arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasProperty(Property arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasProperty(Property arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasProperty(Property arg0, RDFNode arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasProperty(Property arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasURI(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StmtIterator listProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StmtIterator listProperties(Property arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource removeAll(Property arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource removeProperties() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Literal asLiteral() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Resource asResource() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Resource getPropertyResourceValue(Property arg0) {
		// TODO Auto-generated method stub
		return null;
	}



}
