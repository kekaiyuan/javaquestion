import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import com.kky.netty.netty02.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MsgCodecTest {

    @Test
    public void testMsgEncoder() {
        Msg msg = new Msg(10, 10);
        EmbeddedChannel channel = new EmbeddedChannel(new MsgEncoder());
        channel.writeOutbound(msg);

        ByteBuf buf = (ByteBuf) channel.readOutbound();
        int x = buf.readInt();
        int y = buf.readInt();

        Assertions.assertTrue(x == 10 && y == 10);
        buf.release();
    }

    @Test
    public void testMsgEncoder2(){
        ByteBuf buf = Unpooled.buffer();
        Msg msg = new Msg(10, 10);
        buf.writeInt(10);
        buf.writeInt(10);

        EmbeddedChannel channel = new EmbeddedChannel(new MsgEncoder(),new MsgDecoder());
        channel.writeInbound(buf.duplicate());

        Msg m = (Msg) channel.readInbound();

        Assertions.assertTrue(m.x == 10 && m.y == 10);

    }
}
