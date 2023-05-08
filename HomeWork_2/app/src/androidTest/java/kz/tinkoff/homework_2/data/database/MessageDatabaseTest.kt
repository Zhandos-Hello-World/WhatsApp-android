package kz.tinkoff.homework_2.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
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

@ExperimentalCoroutinesApi
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
    fun checkEmptyTest() = runTest {
        val expectedSize = messageDao.getAllMessages(
            "general",
            "test"
        ).size
        assertEquals(0, expectedSize)
    }

    @Test
    fun readAndWrite() = runTest {
        val streamId = 21
        val expectedMessage = MessageEntity(
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
        messageDao.addMessage(expectedMessage)
        val returnedEntity = messageDao.getMessageById(streamId).first()

        assertEquals(expectedMessage, returnedEntity)
    }


    @Test
    fun writeAndCheckListOfEntity() = runTest {
        val streams = 1000
        val expectedMessagesList = mutableListOf<MessageEntity>()
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
            expectedMessagesList.add(entity)
            messageDao.addMessage(entity)
        }
        val returnedList = messageDao.getAllMessages(
            "hello",
            "test"
        )
        assertEquals(expectedMessagesList, returnedList)
    }


    @After
    fun tearDown() {
        db.close()
    }
}
