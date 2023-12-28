package me.amirkazemzade.materialmusicplayer.domain.source

interface RemoteMusicSource : ReadableMusicSource {
    override suspend fun getVersion(): String
}