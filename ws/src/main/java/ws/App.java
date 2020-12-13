package ws;

import org.semanticweb.owlapi.model.OWLOntology;

import ws.tp.OWLAPI_StepByStep;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        //    Initialisation
        OWLAPI_StepByStep.initOntoData(OWLAPI_StepByStep.OntologyFileName);
        

        //    Question 1
        //OWLAPI_StepByStep.read(OWLAPI_StepByStep.OntologyFileName);
        
        
        //    Question 2
        //OWLAPI_StepByStep.printClasses(OWLAPI_StepByStep.OntoData.ontology);

        //    Question 3
        //OWLAPI_StepByStep.printObjectProperty(OWLAPI_StepByStep.OntoData.ontology);

        //    Question 4
        //OWLAPI_StepByStep.printDataTypeProperty(OWLAPI_StepByStep.OntoData.ontology);

        //    Question 5
        //OWLAPI_StepByStep.printIndividuals(OWLAPI_StepByStep.OntoData.ontology);

        //    Question 6
        //String filename = OWLAPI_StepByStep.radical + "/pizza_1_A.owl";
        //OWLAPI_StepByStep.save(OWLAPI_StepByStep.OntoData.ontology, OWLAPI_StepByStep.OntoData.manager, filename);
        //OWLAPI_StepByStep.read(filename);
        
        //    Question 7
        //String filename = OWLAPI_StepByStep.radical + "/pizza_1_B.owl";
        //OWLAPI_StepByStep.saveInOWLXML(OWLAPI_StepByStep.OntoData.ontology, OWLAPI_StepByStep.OntoData.manager, filename);
        //OWLAPI_StepByStep.read(filename);

        //    Question 8
        //String filename_B = OWLAPI_StepByStep.radical + "/pizza_1_B.owl";
        //String filename_AA = OWLAPI_StepByStep.radical + "/pizza_1_AA.owl";
        //OWLOntology ontology = OWLAPI_StepByStep.read(filename_B) ;
        //OWLAPI_StepByStep.saveInRDFXML(ontology, OWLAPI_StepByStep.OntoData.manager, filename_AA);
        //OWLAPI_StepByStep.read(filename_AA);
    }
}
