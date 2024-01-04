package org.robotme.core.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author marcin
 * 
 */
public class VariousUtilsTest {

    @Test
    public void testEqualObjects1() {
        Assert.assertTrue(VariousUtils.equalObjects(null, null));
    }

    @Test
    public void testEqualObjects2() {
        Assert.assertFalse(VariousUtils.equalObjects(new Object(), new Object()));
    }

    @Test
    public void testEqualObjects3() {
        Assert.assertTrue(VariousUtils.equalObjects("123ABC", "123ABC"));
    }

    @Test
    public void testEqualStringsTables1() {
        Assert.assertTrue(VariousUtils.equalStringsTables(new String[] { "123ABC" }, new String[] { "123ABC" }));
    }

    @Test
    public void testEqualStringsTables2() {
        Assert.assertFalse(VariousUtils.equalStringsTables(new String[] { "123ABC", "1" }, new String[] { "123ABC" }));
    }
    
    @Test
    public void testEqualStringsTables3() {
        Assert.assertFalse(VariousUtils.equalStringsTables(new String[]{"123ABC", "1"}, new String[]{"123ABC", "2"}));
    }
    
    @Test
    public void testEqualStringsTables4() {
        Assert.assertFalse(VariousUtils.equalStringsTables(new String[]{"123ABC", "1"}, (String[])null));
    }
    
    @Test
    public void testEqualStringsTables5() {
        Assert.assertFalse(VariousUtils.equalStringsTables((String[])null, new String[]{"123ABC", "1"}));
    }
    
    @Test
    public void testEqualStringsTables6() {
        Assert.assertTrue(VariousUtils.equalStringsTables(new String[0], new String[0]));
    }
    
    @Test
    public void testEqualBooleanTables1() {
        Assert.assertTrue(VariousUtils.equalBooleanTables(new boolean[]{}, new boolean[]{}));
    }
    
    @Test
    public void testEqualBooleanTables2() {
        Assert.assertTrue(VariousUtils.equalBooleanTables(new boolean[]{true}, new boolean[]{true}));
    }
    
    @Test
    public void testEqualBooleanTables3() {
        Assert.assertTrue(VariousUtils.equalBooleanTables(new boolean[]{false}, new boolean[]{false}));
    }
    
    @Test
    public void testEqualBooleanTables4() {
        Assert.assertFalse(VariousUtils.equalBooleanTables(new boolean[]{true}, new boolean[]{false}));
    }
    
    @Test
    public void testEqualBooleanTables5() {
        Assert.assertFalse(VariousUtils.equalBooleanTables(new boolean[]{true}, new boolean[]{true, false}));
    }

}
