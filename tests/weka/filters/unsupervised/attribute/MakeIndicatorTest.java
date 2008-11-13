/*
 * Copyright (C) 2002 University of Waikato 
 */

package weka.filters.unsupervised.attribute;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Attribute;
import weka.core.Instances;
import weka.filters.AbstractFilterTest;
import weka.filters.Filter;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Tests MakeIndicator. Run from the command line with:<p>
 * java weka.filters.MakeIndicatorTest
 *
 * @author <a href="mailto:len@reeltwo.com">Len Trigg</a>
 * @version $Revision: 1.3.2.1 $
 */
public class MakeIndicatorTest extends AbstractFilterTest {
  
  public MakeIndicatorTest(String name) { super(name);  }

  /** Creates an example MakeIndicator */
  public Filter getFilter() {
    MakeIndicator f = new MakeIndicator();
    // Ensure the filter we return can run on the test dataset
    f.setAttributeIndex("2"); 
    return f;
  }

  /**
   * returns the configured FilteredClassifier.
   * 
   * @return the configured FilteredClassifier
   */
  protected FilteredClassifier getFilteredClassifier() {
    FilteredClassifier	result;
    
    result = super.getFilteredClassifier();
    ((MakeIndicator) result.getFilter()).setAttributeIndex("3");
    
    return result;
  }

  public void testInvalidAttributeTypes() {
    Instances icopy = new Instances(m_Instances);
    try {
      ((MakeIndicator)m_Filter).setAttributeIndex("1");
      m_Filter.setInputFormat(icopy);
      fail("Should have thrown an exception selecting a STRING attribute!");
    } catch (Exception ex) {
      // OK
    }
    try {
      ((MakeIndicator)m_Filter).setAttributeIndex("3");
      m_Filter.setInputFormat(icopy);
      fail("Should have thrown an exception indicating a NUMERIC attribute!");
    } catch (Exception ex) {
      // OK
    }
  }

  public void testDefault() {
    ((MakeIndicator)m_Filter).setAttributeIndex("2");
    Instances result = useFilter();
    assertEquals(m_Instances.numAttributes(), result.numAttributes());
    assertEquals(m_Instances.numInstances(),  result.numInstances());
    // Check that default attribute type is numeric
    assertEquals("Default attribute encoding should be NUMERIC",
                 Attribute.NUMERIC, result.attribute(1).type());
    // Check that default indication is correct
    for (int i = 0; i < result.numInstances(); i++) {
      assertTrue("Checking indicator for instance: " + (i + 1),
             (m_Instances.instance(i).value(1) == 2) ==
             (result.instance(i).value(1) == 1));
    }
  }

  public void testNominalEncoding() {
    ((MakeIndicator)m_Filter).setAttributeIndex("2");
    ((MakeIndicator)m_Filter).setNumeric(false);    
    Instances result = useFilter();
    assertEquals(m_Instances.numAttributes(), result.numAttributes());
    assertEquals(m_Instances.numInstances(),  result.numInstances());
    // Check that default attribute type is numeric
    assertEquals("New attribute encoding should be NOMINAL",
                 Attribute.NOMINAL, result.attribute(1).type());
    // Check that default indication is correct
    for (int i = 0; i < result.numInstances(); i++) {
      assertTrue("Checking indicator for instance: " + (i + 1),
             (m_Instances.instance(i).value(1) == 2) ==
             (result.instance(i).value(1) == 1));
    }
  }

  public void testMultiValueIndication() {
    ((MakeIndicator)m_Filter).setAttributeIndex("2");
    try {
      ((MakeIndicator)m_Filter).setValueIndices("1,3");
    } catch (Exception ex) {
      fail("Is Range broken?");
    }
    ((MakeIndicator)m_Filter).setNumeric(false);    
    Instances result = useFilter();
    assertEquals(m_Instances.numAttributes(), result.numAttributes());
    assertEquals(m_Instances.numInstances(),  result.numInstances());
    // Check that default attribute type is numeric
    assertEquals("New attribute encoding should be NOMINAL",
                 Attribute.NOMINAL, result.attribute(1).type());
    // Check that default indication is correct
    for (int i = 0; i < result.numInstances(); i++) {
      assertTrue("Checking indicator for instance: " + (i + 1),
             ((m_Instances.instance(i).value(1) == 0) ||
              (m_Instances.instance(i).value(1) == 2)) 
             ==
             (result.instance(i).value(1) == 1));
    }
  }

  public static Test suite() {
    return new TestSuite(MakeIndicatorTest.class);
  }

  public static void main(String[] args){
    junit.textui.TestRunner.run(suite());
  }

}
