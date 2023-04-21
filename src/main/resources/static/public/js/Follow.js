console.log("포함됨-");

//팔로잉 링크버튼
async function following(toId,btn){
    let status=await registerFollow(toId,false);
    if(status==="1"){//따라서 반환값에 따라 실제 정말 성공했는지의 여부를 체크한다.
        alert("팔로우성공!");
        btn.closest(".followCont").remove();
    }else{
        alert("팔로우 실패");
    }

}

//처리만 해줌.
async function registerFollow(toId,follower){
    //from=로그인 유저 to=팔로우(구독)하고싶은 유저
    //로그인유저는 기본 유저정보에 담겨져 오기 때문에 to_id를 받아오면 됨!
    let url=`/follow/${toId}/${follower}/handler.do`
    //alert(url);//예를들어 user01이 user03의 팔로우 버튼을 누르면 user03의 정보가 기입된다.
    const resp=await fetch(url,{method:"POST"})
    if(resp.status===200){
        let status=await resp.text();//"0"or "1"
        return status; //성공시 정보만 받아봄."0" or "1"
    }else if(resp.status===400){
        alert("로그인 하셔야 이용할 수 있는 서비스 입니다.");
    }else if(resp.status===500){//팔로잉을 했는데 한번 더 팔로잉하면 db 오류  또는 통신오류가 발생한다.
        alert("이미 팔로잉 되었거나 오류가 발생했으니 새로고침하고 다시 시도하세요~");
    }
}

async function removeFollow(fromId,follower){
    //follower true->팔로워삭제 false->팔로잉삭제
    let url=`/follow/${fromId}/${follower}/handler.do`
    let method="DELETE";
    const resp=await fetch(url,{method:method});
    if(resp.status===200){
        return await resp.text();
   }else if(resp.status===400){
        alert("로그인 하셔야 이용할 수 있는 서비스 입니다.");
    }else if(resp.status===500){
        alert("이미 팔로잉 되었거나 오류가 발생했으니 새로고침하고 다시 시도하세요~");
    }

}
async function toggleFollower(fromId,btn){
    let active=(btn.classList.contains("active"));
    if(active){//이미 삭제됨(팔로워를 다시등록)
        //btn.classList.remove("active")
        //alert("팔로워 삭제 취소!")
        let register=await registerFollow(fromId,true);
        if(register==="1"){
            btn.classList.remove("active");
            alert("팔로우 삭제 취소(Re follow)");//실제로 삭제를 번복하는것은 없고 삭제후 재등록하는것.
        }else{
            alert("팔로우 삭제 취소 불가");
        }
        
    }else{//아직 삭제되지 않음
        let remove=await removeFollow(fromId,true);
        if(remove==="1"){
            btn.classList.add("active");
            alert("팔로워 삭제 성공!")
        }else{
            alert("팔로워 삭제 실패")
        }
    }

}
async function toggleFollowing(toId,btn){
    //console.log(toId,btn);
    let active=btn.classList.contains("active");
    if(active){//팔로잉취소
        let remove=await removeFollow(toId,false);
        if(remove>0){
            alert("팔로잉 취소 성공!");
            btn.classList.remove("active");
        }else{
            alert("팔로잉 취소 실패");
        }
    }else{//다시 팔로잉
        let register=await registerFollow(toId,false);
        if(register>0){//문자열을 비교연산하면 수로 형변환 가능 (js기능..)
            alert("팔로잉 성공");
            btn.classList.add("active")
        }else{
            alert("팔로잉 실패")
        }
    }
}