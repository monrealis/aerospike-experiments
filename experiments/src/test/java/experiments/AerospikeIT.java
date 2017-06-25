package experiments;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;

public class AerospikeIT {
	private final String namespace = "test";
	private final String set = "demo";
	private AerospikeClient client;
	private Key key = new Key(namespace, set, "1");

	@Before
	public void before() {
		client = new AerospikeClient("localhost", 3000);
	}

	@After
	public void after() {
		client.close();
	}

	@Test
	public void saveOne() {
		save();
		load();
	}

	@Test
	public void saveToDifferentSets() {
		save(new Key(namespace, set, "1"));
		save(new Key(namespace, "set2", "2"));
		save(new Key(namespace, "set2", "2"));
		save(new Key(namespace, null, "1"));
		save(new Key(namespace, null, "1"));
	}

	@Test
	public void saveMany() {
		for (int i = 0; i < 1 * 1000; ++i)
			save();
	}

	@Test
	public void query() {
		Statement statement = new Statement();
		statement.setNamespace(namespace);
		statement.setSetName("demo");
		RecordSet result = client.query(null, statement);
		while (result.next())
			System.out.println(result.getRecord());
	}

	private void save() {
		save(key);
	}

	private void save(Key key) {
		Bin bin1 = new Bin("bin1", "value1");
		Bin bin2 = new Bin("bin2", "value2");
		client.put(null, key, bin1, bin2);
	}

	private void load() {
		Record record = client.get(null, key);
		assertNotNull(record);
	}
}
