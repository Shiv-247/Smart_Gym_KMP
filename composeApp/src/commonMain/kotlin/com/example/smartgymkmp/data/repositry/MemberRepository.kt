import com.example.smartgymkmp.data.local.MemberDao
import com.example.smartgymkmp.domain.model.ActivityLevel
import com.example.smartgymkmp.domain.model.CalorieResult
import com.example.smartgymkmp.domain.model.Member
import com.example.smartgymkmp.model.MemberEntity

interface MemberRepository {
    suspend fun getMembers(): List<Member>
    suspend fun addMember(member: Member)
    suspend fun calculateCalories(memberId: String, activityLevel: ActivityLevel): CalorieResult

    suspend fun ensureMemberExists(memberId: String)
}