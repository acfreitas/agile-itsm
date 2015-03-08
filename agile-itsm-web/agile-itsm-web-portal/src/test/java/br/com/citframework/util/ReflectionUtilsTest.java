package br.com.citframework.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.Test;

/**
 * Classe de testes para validação do comportamento de {@link ReflectionUtils}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 18/08/2014
 *
 */
public final class ReflectionUtilsTest {

    @Test
    public void findField() {
        Field field = ReflectionUtils.findField(TestObjectSubclassWithPublicField.class, "publicField", String.class);
        assertNotNull(field);
        assertEquals("publicField", field.getName());
        assertEquals(String.class, field.getType());
        assertTrue("Field should be public.", Modifier.isPublic(field.getModifiers()));

        field = ReflectionUtils.findField(TestObjectSubclassWithNewField.class, "prot", String.class);
        assertNotNull(field);
        assertEquals("prot", field.getName());
        assertEquals(String.class, field.getType());
        assertTrue("Field should be protected.", Modifier.isProtected(field.getModifiers()));

        field = ReflectionUtils.findField(TestObjectSubclassWithNewField.class, "name", String.class);
        assertNotNull(field);
        assertEquals("name", field.getName());
        assertEquals(String.class, field.getType());
        assertTrue("Field should be private.", Modifier.isPrivate(field.getModifiers()));
    }

    @Test
    public void setField() {
        final TestObjectSubclassWithNewField testBean = new TestObjectSubclassWithNewField();
        final Field field = ReflectionUtils.findField(TestObjectSubclassWithNewField.class, "name", String.class);

        ReflectionUtils.makeAccessible(field);

        ReflectionUtils.setField(field, testBean, "FooBar");
        assertNotNull(testBean.getName());
        assertEquals("FooBar", testBean.getName());

        ReflectionUtils.setField(field, testBean, null);
        assertNull(testBean.getName());
    }

    @Test(expected = IllegalStateException.class)
    public void setFieldIllegal() {
        final TestObjectSubclassWithNewField testBean = new TestObjectSubclassWithNewField();
        final Field field = ReflectionUtils.findField(TestObjectSubclassWithNewField.class, "name", String.class);
        ReflectionUtils.setField(field, testBean, "FooBar");
    }

    @Test
    public void invokeMethod() throws Exception {
        final String rob = "Rob Harrop";

        final TestObject bean = new TestObject();
        bean.setName(rob);

        final Method setName = TestObject.class.getMethod("setName", new Class[] {String.class});

        final String juergen = "Juergen Hoeller";
        ReflectionUtils.invokeMethod(setName, bean, new Object[] {juergen});
        assertEquals("Incorrect name set", juergen, bean.getName());
    }

    @Test
    public void findMethod() throws Exception {
        assertNotNull(ReflectionUtils.findMethod(B.class, "bar", String.class));
        assertNotNull(ReflectionUtils.findMethod(B.class, "foo", Integer.class));
    }

    @SuppressWarnings("unused")
    private static class TestObject {

        private String name;
        private Integer age;
        private TestObject spouse;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(final Integer age) {
            this.age = age;
        }

        public TestObject getSpouse() {
            return spouse;
        }

        public void setSpouse(final TestObject spouse) {
            this.spouse = spouse;
        }

    }

    private static class TestObjectSubclassWithPublicField extends TestObject {

        @SuppressWarnings("unused")
        public String publicField = "foo";

    }

    @SuppressWarnings("unused")
    private static class TestObjectSubclassWithNewField extends TestObject {

        private int magic;

        protected String prot = "foo";
    }

    private static class A {

        @SuppressWarnings("unused")
        private void foo(final Integer i) {}

    }

    @SuppressWarnings("unused")
    private static class B extends A {

        void bar(final String s) throws IllegalArgumentException {}

        int add(final int... args) {
            int sum = 0;
            for (final int arg : args) {
                sum += arg;
            }
            return sum;
        }

    }

}
