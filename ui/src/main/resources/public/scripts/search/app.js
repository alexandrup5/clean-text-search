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
            $("#results").html('');
                    
            axios.post(url, {
                    "originalText": originalText,
                    "cleanText": cleanText,
                    "selectStartIndex": selectedText.selectStartIndex,
                    "selectEndIndex": selectedText.selectEndIndex
                },
                { headers: {"Access-Control-Allow-Origin": "*"}})   
                .then(response => {
                    this.selectText(response.data);
                })
                .catch(error => {
                    console.log(error);
                });
        },

        selectedText(){
            var dom = document.getElementById("original-textarea");
            var start = dom.selectionStart;
            var finish = dom.selectionEnd;
            return {
                selectStartIndex: start,
                selectEndIndex: finish
            }
        },

        selectText(bounds){
            var innerText = $("#clean-textarea").val();

            bounds.forEach((bound, idx) => {
                var resultText = "";
                var lastIndex = 0;

                if (bound.start !== 0){
                    resultText = innerText.substring(0, bound.start -1);
                }

                resultText += "[" + innerText.substring(bound.start -1, bound.end) + "]";    
                lastIndex = bound.end;   
  
                resultText += innerText.substring(lastIndex, innerText.length);
                
                $("#results").append("<li>" + resultText + "</li>");
            });

        
        },
    },
    template: `
        <div class="container">
            <div class="row text">
                <textarea id="original-textarea" v-model="originalText" @click="searchFromOriginalText(originalText, cleanText, selectedText())" class="rounded my-5 w-50 p3" rows="10"/>
                <textarea id="clean-textarea" v-model="cleanText" class="rounded my-5 w-50 p3" rows="10"/>

                <div id="results">
                </div>
            </div>
        </div>
    `
});