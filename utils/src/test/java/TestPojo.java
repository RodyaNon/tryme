import com.rodya.PojoUtil;

public class TestPojo {

    public static void main(String[] args) {

        PojoUtil.copy(new TestPojo(), TestPojo.class);
    }
}
