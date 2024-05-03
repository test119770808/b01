async function get1(bno) {   // async ->  비동기 처리함수 명시...
    const result = await axios.get(`/replies/list/${bno}`)   // await  -> 비동기 호출
    // console.log(result)
    // console.log(result.data)
    return result.data;
}

async function getList({bno, page, size, goLast}){
    // console.log(bno, page, size)
    const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}})
    // console.log(result)
    if(goLast) {
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        return getList({bno:bno, page:lastPage, size:size})
    }

    return result.data
}

async function addReply(replyObj){
    const response = await axios.post(`/replies/`, replyObj)
    return response.data
}

async function getReply(rno) {
    const response = await axios.get(`/replies/${rno}`)
    console.log(response)
    return response.data
}

async function modifyReply(replyObj) {
    const response = await axios.put(`/replies/${replyObj.rno}`, replyObj)
    return response.data
}

async function removeReply(rno) {
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}


