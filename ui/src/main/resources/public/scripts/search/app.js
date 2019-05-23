new Vue({
    el: "#app",
    data: {
        originalText: `This is the original text, before cleaning the text             
            ++++++++++++                      *****       ^^^^^                
            ++++++++++++*****^^^^^   `,
        cleanText: "This is the clean text"
    },
    methods: {
        searchFromOriginalText(originalText, cleanText, selectedText){

            const url = "/api/text/search/by-original";
                    
            axios.post(url, {
                    "originalText": originalText,
                    "cleanText": cleanText,
                    "selectedText": selectedText
                },
                { headers: {"Access-Control-Allow-Origin": "*"}})
                .then(response => {
                    selectText(response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        },

        selectedText(){
            var dom = document.getElementById("original-textarea");
            var start = dom.selectionStart;
            var finish = dom.selectionEnd;
            return dom.value.substring(start, finish);
        },

        selectText(indexes){

        }
    },
    computed: {
    },
    template: `
        <div class="container">
            <div class="row text">
                <textarea id="original-textarea" v-model="originalText" @click="searchFromOriginalText(originalText, cleanText, selectedText())" class="rounded my-5 w-50 p3" rows="10"/>
                <textarea id="clean-textarea" v-model="cleanText" class="rounded my-5 w-50 p3" rows="10"/>
            </div>
        </div>
    `
});