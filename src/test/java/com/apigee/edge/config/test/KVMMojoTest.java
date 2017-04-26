import com.apigee.edge.config.mavenplugin.KVMMojo;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import java.io.File;

public class KVMMojoTest
    extends AbstractMojoTestCase
{
    /** {@inheritDoc} */
    protected void setUp()
        throws Exception
    {
        // required
        super.setUp();
    }

    /** {@inheritDoc} */
    protected void tearDown()
        throws Exception
    {
        // required
        super.tearDown();
    }

    /**
     * @throws Exception if any
     */
    public void testSomething()
        throws Exception
    {
        File pom = getTestFile( "src/test/resources/unit/EdgeConfig/pom.xml" );
        assertNotNull( pom );
        assertTrue( pom.exists() );

        KVMMojo kvmMojo = (KVMMojo) lookupMojo( "kvms", pom );
        assertNotNull( kvmMojo );
        kvmMojo.execute();
    }
}