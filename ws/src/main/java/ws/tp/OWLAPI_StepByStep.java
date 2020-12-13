package ws.tp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
import org.mindswap.pellet.KnowledgeBase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.dlsyntax.renderer.DLSyntaxObjectRenderer;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.PrefixDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.PrefixManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDArgument;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.search.Searcher;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.InferredAxiomGenerator;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.util.InferredSubClassAxiomGenerator;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.swrlapi.core.SWRLAPIRule;
import org.swrlapi.core.SWRLRuleEngine;
import org.swrlapi.exceptions.LiteralException;
import org.swrlapi.exceptions.SWRLBuiltInException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;
import org.swrlapi.sqwrl.values.SQWRLLiteralResultValue;

import com.clarkparsia.pellet.expressivity.ExpressivityChecker;
import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.google.common.collect.Multimap;
//import com.microsoft.schemas.office.x2006.encryption.CTKeyEncryptor.Uri;
import com.hp.hpl.jena.sparql.function.library.print;


public class OWLAPI_StepByStep {
    private static OWLObjectRenderer renderer= new DLSyntaxObjectRenderer();

    public static final String radical = "file:///home/medard/Bureau/web-sem/tp3";
    public static final String OntologyFileName = radical + "/pizza_1.owl";
    
    public static class OntoData {
        public static OWLOntology ontology = null;
        public static OWLOntologyManager manager = null;
    }

    /**
     * Cette méthode initialise champs ontoloy et manager de la classe OntoData.
     * @param filename
     */
    public static void initOntoData(String filename){
        if(OntoData.manager == null)
            OntoData.manager = OWLManager.createOWLOntologyManager();     
        try {
            IRI ontologyIRI = IRI.create(filename);
            if(OntoData.manager.contains(ontologyIRI)){
                OntoData.ontology = OntoData.manager.getOntology(ontologyIRI);
            }
            else{
                OntoData.ontology = OntoData.manager.loadOntologyFromOntologyDocument(ontologyIRI);
            }             
        } catch (Exception e) {
            System.out.println("problem reading ontology from " + filename);
            System.out.println(e.getMessage());
        }
    }


    public static OWLOntology read(String filename){
        System.out.println("\n################### appel fonction read #####################");
        initOntoData(filename);
        if (OntoData.ontology != null) {
            OWLDocumentFormat format = OntoData.manager.getOntologyFormat(OntoData.ontology);
            System.out.println("Nombre d'axiomes " + OntoData.ontology.getAxiomCount());
            System.out.println("Ontology format " + format);
            System.out.println("IRI" + OntoData.manager.getOntologyDocumentIRI(OntoData.ontology));
        }
        return OntoData.ontology;
    }

    public static void printClasses(OWLOntology ontology) {
        System.out.println("\n################### appel fonction printClasses #####################");
        System.out.println("Liste des classe");
        Set<OWLClass> cl = ontology.getClassesInSignature();
        cl.stream()
            .forEach(c -> {
                System.out.println(c.getIRI().getFragment());
            });
        System.out.println("\nNombre de classes " + cl.size());
    }

    public static void printObjectProperty(OWLOntology ontology) {
        System.out.println("\n################### appel fonction printObjectProperty #####################");
        System.out.println("Liste des ObjectProperty");
        Set<OWLObjectProperty> cl = ontology.getObjectPropertiesInSignature();
        cl.stream()
            .forEach(c -> {
                System.out.println(c.getIRI().getFragment());
            });
        System.out.println("\nNombre d' ObjectProperty " + cl.size());
    }

    public static void printDataTypeProperty(OWLOntology ontology) {
        System.out.println("\n################### appel fonction printDataTypeProperty #####################");
        System.out.println("Liste des DataTypeProperty");
        Set<OWLDataProperty> cl = ontology.getDataPropertiesInSignature();
        cl.stream()
            .forEach(c -> {
                System.out.println(c.getIRI().getFragment());
            });
        System.out.println("\nNombre de DataTypeProperty " + cl.size());
    }

    public static void printIndividuals(OWLOntology ontology) {
        System.out.println("\n################### appel fonction printIndividuals #####################");
        System.out.println("Liste des Individuals");
        Set<OWLNamedIndividual> cl = ontology.getIndividualsInSignature();
        cl.stream()
            .forEach(c -> {
                System.out.println(c.getIRI().getFragment());
            });
        System.out.println("\nNombre de Individuals " + cl.size());
        //System.out.println("anonymes "+ontology.getAnonymousIndividuals().size());
    } 

    private static void save(OWLOntology ontology, OWLOntologyManager manager, String savedOntologyFilename, OWLDocumentFormat format) {
        IRI ontologyIRI = IRI.create(savedOntologyFilename); 
        try { 
            OWLOntology newOntology = manager.createOntology(ontology.getAxioms(), ontologyIRI) ;
            manager.setOntologyFormat(newOntology, format);
            newOntology.saveOntology();
            //manager.saveOntology(ontology, new TurtleDocumentFormat(), savedOntologyIRI);
        } catch (OWLOntologyStorageException e) {
            System.out.println("Error ontology saving" + e.getMessage());
        }
        catch (OWLOntologyCreationException e) {
            System.out.println("Error ontology creation" + e.getMessage());
        }
    }

    public static void save(OWLOntology ontology, OWLOntologyManager manager, String savedOntologyFilename) {
        System.out.println("\n################### appel fonction save #####################");
        OWLDocumentFormat format = manager.getOntologyFormat(ontology) ;
        save(ontology, manager, savedOntologyFilename, format);
        System.out.println("Sauvegarde terminée");
    }

    public static void saveInOWLXML(OWLOntology ontology, OWLOntologyManager manager, String savedOntologyFilename) {
        System.out.println("\n################### appel fonction saveInOWLXML #####################");
        OWLDocumentFormat format = new OWLXMLDocumentFormat();
        save(ontology, manager, savedOntologyFilename, format);
        System.out.println("Sauvegarde terminée");
    }

    public static void saveInRDFXML(OWLOntology ontology, OWLOntologyManager manager, String savedOntologyFilename) {
        System.out.println("\n################### appel fonction saveInRDFXML #####################");
        OWLDocumentFormat format = new RDFXMLDocumentFormat();
        save(ontology, manager, savedOntologyFilename, format);
        System.out.println("Sauvegarde terminée");
    }

    public static void addingClassesAndComment(OWLOntology ontology, OWLOntologyManager manager) {
        
    }
}