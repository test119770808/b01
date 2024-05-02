async function get1(bno) {   // async ->  비동기 처리함수 명시...
    const result = await axios.get(`/replies/list/${bno}`)   // await  -> 비동기 호출
    // console.log(result)
    // console.log(result.data)
    return result.data;
}

async function getList({bno, page, size, goLast}){
    // console.log(bno, page, size)
    const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}})

    return result.data
}