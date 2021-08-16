import com.mby.algorithm.LRUAlgoCacheImpl;
import com.mby.dm.DataModel;
import com.mby.dao.DaoFileImpl;
import com.mby.memory.CacheUnit;
import org.junit.Assert;
import org.junit.Test;

public class CacheUnitTest {
    @Test
    public void DaoFileImpTest() throws Exception {
        DataModel<String> element = new DataModel<>((long) 1234, "Michal");

        DaoFileImpl<String> file = new DaoFileImpl<>("src\\main\\resources\\dataSource.txt");
        file.save(element);
        element.setContent("Raily");
        element.setDataModelId((long) 1111);
        file.save(element);
        file.save(element);
        Assert.assertEquals("Michal", file.find((long) 1234).getContent());
        Assert.assertEquals("Raily", file.find((long) 1111).getContent());
        file.delete(element);
        Assert.assertEquals("Michal", file.find((long) 1234).getContent());
        Assert.assertNull(file.find((long) 1111));
        DataModel<String> element1 = new DataModel<>((long) 1234, "Michal");
        DaoFileImpl<String> file1 = new DaoFileImpl<>("src\\main\\resources\\dataSource.txt", 3);
        file1.save(element1);
        Assert.assertEquals("Michal", element1.getContent());
        Long a = 1234L;
        Assert.assertEquals(a, element1.getDataModelId());
        Assert.assertEquals("DataModel{content=Michal, DataModelId=1234}", element1.toString());

    }

    @Test
    public void CacheUnitImpTest() {
        LRUAlgoCacheImpl<Long, DataModel<String>> lru = new LRUAlgoCacheImpl<Long, DataModel<String>>(3);
        DataModel<String>[] dataModels = new DataModel[3];
        dataModels[0] = new DataModel<String>((long) 111, "Michal");
        dataModels[1] = new DataModel<String>((long) 222, "Raily");
        dataModels[2] = new DataModel<String>((long) 333, "Efrat");
        CacheUnit<String> cache = new CacheUnit<>(lru);
        cache.putDataModels(dataModels);
        Long[] ids = {(long) 111, (long) 222, (long) 333};
        DataModel<String>[] test = cache.getDataModels(ids);
        Assert.assertEquals("Michal", test[0].getContent());
        Assert.assertEquals("Raily", test[1].getContent());
        Assert.assertEquals("Efrat", test[2].getContent());
        cache.removeDataModels(ids);
    }
}