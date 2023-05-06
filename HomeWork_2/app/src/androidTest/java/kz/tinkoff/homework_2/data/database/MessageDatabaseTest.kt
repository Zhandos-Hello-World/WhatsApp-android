package kz.tinkoff.homework_2.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kz.tinkoff.homework_2.data.dao.MessageDao
import kz.tinkoff.homework_2.data.enitiy.MessageEntity
import kz.tinkoff.homework_2.data.enitiy.ReactionEntity
import kz.tinkoff.homework_2.data.enitiy.ReactionListEntity
import kz.tinkoff.homework_2.data.enitiy.UserEntity
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MessageDatabaseTest {

    private lateinit var messageDao: MessageDao
    private lateinit var db: MessageDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MessageDatabase::class.java
        ).build()
        messageDao = db.dao()
    }

    @Test
    fun checkEmptyTest() = runBlocking {
        val size = messageDao.getAllMessages(
            "general",
            "test"
        ).size
        assertEquals(0, size)
    }

    @Test
    fun readAndWrite() = runBlocking {
        val streamId = 21
        val message = MessageEntity(
            id = streamId,
            senderId = 435,
            recipientId = 2454,
            timestamp = System.currentTimeMillis(),
            topic = "test",
            isMeMessage = false,
            senderFullName = "Tester",
            senderEmail = "test@test",
            senderRealmStr = "gew",
            displayRecipient = "hello",
            type = "message",
            content = "<p>Hello world</p>",
            streamId = 234,
            avatarUrl = "avatar url",
            reactions = ReactionListEntity(
                listOf(
                    ReactionEntity(
                        id = 10,
                        emojiName = "smile",
                        emojiCode = "😀",
                        reactionType = "f",
                        user = UserEntity(1, "", "", true)
                    )
                )
            )
        )
        messageDao.addMessage(message)
        val returnedEntity = messageDao.getMessageById(streamId).first()

        assertEquals(message, returnedEntity)
    }


    @Test
    fun writeAndCheckListOfEntity() = runBlocking {
        val streams = 1000
        val list = mutableListOf<MessageEntity>()
        for (i in 0..streams) {
            val entity = MessageEntity(
                id = i,
                senderId = 435,
                recipientId = 2454,
                timestamp = System.currentTimeMillis(),
                topic = "test",
                isMeMessage = false,
                senderFullName = "Tester",
                senderEmail = "test@test",
                senderRealmStr = "gew",
                displayRecipient = "hello",
                type = "message",
                content = "<p>Hello world</p>",
                streamId = 234,
                avatarUrl = "avatar url",
                reactions = ReactionListEntity(
                    listOf(
                        ReactionEntity(
                            id = 10,
                            emojiName = "smile",
                            emojiCode = "😀",
                            reactionType = "f",
                            user = UserEntity(1, "", "", true)
                        )
                    )
                )
            )
            list.add(entity)
            messageDao.addMessage(entity)
        }
        val returnedList = messageDao.getAllMessages(
            "hello",
            "test"
        )
        assertEquals(list, returnedList)
    }


    @After
    fun tearDown() {
        db.close()
    }

}
