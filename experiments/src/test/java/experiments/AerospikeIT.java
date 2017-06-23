package experiments;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;

public class AerospikeIT {
	private AerospikeClient client;
	private Key key = new Key("test", "demo", "1");

	@Before
	public void before() {
		client = new AerospikeClient("localhost", 3000);
	}

	@After
	public void after() {
		client.close();
	}

	@Test
	public void run() {
		save();
		Record record = client.get(null, key);
		assertNotNull(record);
	}

	private void save() {
		Bin bin1 = new Bin("bin1", "value1");
		Bin bin2 = new Bin("bin2", "value2");
		client.put(null, key, bin1, bin2);
	}
}
