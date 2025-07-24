import com.example.smartgymkmp.data.local.MemberDao
import com.example.smartgymkmp.model.MemberEntity

class MemberRepository(private val dao: MemberDao) {
    fun getAllMembers() = dao.getAllMembers()
    fun getMemberById(id: String) = dao.getMemberById(id)
    suspend fun insert(member: MemberEntity) = dao.insertMember(member)
    suspend fun delete(member: MemberEntity) = dao.deleteMember(member)
    suspend fun update(member: MemberEntity) { dao.updateMember(member) }
    suspend fun insertAll(members: List<MemberEntity>) { dao.insertAll(members) }
}
